import SwiftUI
import ReSwift
struct FrameCallbackView: UIViewRepresentable {
    let onFrame: () -> Void

    func makeUIView(context: Context) -> UIView {
        let view = CallbackUIView()
        view.onFrame = onFrame
        return view
    }

    func updateUIView(_ uiView: UIView, context: Context) {}

    class CallbackUIView: UIView {
        var onFrame: (() -> Void)?
        private var displayLink: CADisplayLink?

        override func didMoveToSuperview() {
            super.didMoveToSuperview()

            if superview != nil {
                startFrameUpdates()
            } else {
                stopFrameUpdates()
            }
        }

        private func startFrameUpdates() {
            if displayLink == nil {
                displayLink = CADisplayLink(target: self, selector: #selector(frameUpdate))
                displayLink?.add(to: .main, forMode: .default)
            }
        }

        private func stopFrameUpdates() {
            displayLink?.invalidate()
            displayLink = nil
        }

        @objc private func frameUpdate() {
            onFrame?()
        }

        deinit {
            stopFrameUpdates()
        }
    }
}


// MARK: - State
struct CounterState: Equatable {
    var counter: Int = 0
    var rate: Float = 0.0
    var timestamps: [TimeInterval] = []

    static func == (lhs: CounterState, rhs: CounterState) -> Bool {
        return lhs.counter == rhs.counter &&
            lhs.rate == rhs.rate &&
            lhs.timestamps == rhs.timestamps
    }
}

// MARK: - Actions
struct IncrementAction: Action {}

// MARK: - Reducer
let windowDuration: TimeInterval = 5.0

func counterReducer(action: Action, state: CounterState?) -> CounterState {
    var state = state ?? CounterState()

    switch action {
    case _ as IncrementAction:
        let currentTime = Date().timeIntervalSince1970
        let newTimestamps = state.timestamps.filter { $0 > currentTime - windowDuration } + [currentTime]

        let rate = newTimestamps.count > 1
            ? Float(newTimestamps.count) / Float(windowDuration)
            : 0.0

        state = CounterState(
            counter: state.counter + 1,
            rate: rate,
            timestamps: newTimestamps
        )
    default:
        break
    }

    return state
}

// MARK: - Store
let store = Store<CounterState>(
    reducer: counterReducer,
    state: CounterState()
)

// MARK: - ViewStore
class ViewStore: ObservableObject, StoreSubscriber {
    @Published var state: CounterState

    init(store: Store<CounterState>) {
        self.state = store.state
        store.subscribe(self)
    }

    func newState(state: CounterState) {
        DispatchQueue.main.async {
            self.state = state
        }
    }

    func dispatch(_ action: Action) {
        store.dispatch(action)
    }

    deinit {
        store.unsubscribe(self)
    }
}

// MARK: - CounterApp View
struct CounterAppSwiftUI: View {
    @ObservedObject private var viewStore = ViewStore(store: store)

    var body: some View {
        ScrollView {
            LazyVStack(alignment: .leading, spacing: 0) {
                ForEach(0..<3, id: \.self) { index in
                    // Counter Display
                    Text("Counter: \(viewStore.state.counter)")
                        .font(.system(size: 100))
                        .padding(.zero)
                    
                    // FPS Display
                    Text("FPS: \(String(format: "%.2f", viewStore.state.rate))")
                        .font(.system(size: 20))
                        .padding(.zero)
                    
                    FrameCallbackView {
                        //print("frame for index \(index)")
                        viewStore.dispatch(IncrementAction())
                    }
                    
                }
            }
        }
        
            
    }

}
