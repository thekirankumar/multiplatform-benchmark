import React from 'react';
import { View, Text } from 'react-native';
import Timer from './Timer';

function handleUsecase(useCaseID: any) {
  switch(useCaseID) {
    case "redux_counter_id":
      return <Timer />
    default:
      return <Text>Usecase {useCaseID} unhandled!</Text>
  }
}


function App(props): React.JSX.Element {
  const { useCaseID } = props;
  return (
    <View>
    { handleUsecase(useCaseID) };
    </View>
  );
}


export default App;

