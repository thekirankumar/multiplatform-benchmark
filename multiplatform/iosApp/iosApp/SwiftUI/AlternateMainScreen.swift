import SwiftUI
import shared
import React_RCTAppDelegate



struct MainScreen: View {
    var body: some View {
        NavigationView {
            VStack(spacing: 16) {
                NavigationLink(destination: ComposeView(useCaseId: "image_remote_id", closeable: true).navigationTitle("Compose")) {
                    Text("Compose")
                        .font(.system(size: 20))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }

                NavigationLink(destination: ReactView(useCaseId: nil).navigationTitle("React Native")) {
                    Text("React Native")
                        .font(.system(size: 20))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
                NavigationLink(destination: RemoteImageGrid().navigationTitle("Swift UI")) {
                    Text("SwiftUI")
                        .font(.system(size: 20))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.orange)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
            }
            .padding()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemBackground))
            .edgesIgnoringSafeArea(.all)
        }
    }
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
