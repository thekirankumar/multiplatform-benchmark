// create a redux component
// create a store with counter
// it should show a counter value
// and fire an action as soon as it re-renders
// which increments the counter
// all the things should be in this file itself
import React, { useEffect, useRef } from 'react';
import { createStore } from 'redux';
import { Provider, useDispatch, useSelector } from 'react-redux';
import { View, Text } from 'react-native';

// Actions
const INCREMENT = 'INCREMENT';

const increment = () => ({
    type: INCREMENT,
});
// Constants
const WINDOW_DURATION_MS = 5000; // 5 seconds

// Initial State
const initialState = {
    counter: 0,
    timestamps: [], // Tracks increment timestamps
    rate: 0 // Moving window rate
};

// Reducer
const counterReducer = (state = initialState, action) => {
    switch (action.type) {
        case "INCREMENT":
            const currentTime = new Date().getTime();

            // Filter timestamps to keep only those within the window
            const newTimestamps = state.timestamps.filter(
                (timestamp) => timestamp > currentTime - WINDOW_DURATION_MS
            );

            // Add current timestamp
            newTimestamps.push(currentTime);

            // Calculate rate as increments per second within the window
            const rate = (newTimestamps.length / (WINDOW_DURATION_MS / 1000)).toFixed(2);

            return {
                ...state,
                counter: state.counter + 1,
                timestamps: newTimestamps,
                rate: parseFloat(rate)
            };

        default:
            return state;
    }
};

// Store
const store = createStore(counterReducer);

// Component
const Counter: React.FC = () => {
    const dispatch = useDispatch();
    const counter = useSelector((state: any) => state.counter);
    const rate = useSelector((state: any) => state.rate);

    useEffect(() => {
        const interval = setInterval(() => {
            dispatch(increment());
        }, 0); // Increment every 1 millisecond

        return () => {
            clearInterval(interval); // Clean up the interval on component unmount
        };
    }, [dispatch]); 
    return (
        <View>
            <Text style={{fontSize: 100}}>Counter: {counter}</Text>
            <Text style={{fontSize: 20}}>FPS: {rate}</Text>
        </View>
    );
};

// App
function CounterApp(props): React.JSX.Element {
    return (
    <Provider store={store}>
        <Counter />
        <Counter />
        <Counter />
    </Provider>
)};

export default CounterApp;