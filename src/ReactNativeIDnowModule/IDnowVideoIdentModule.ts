import {NativeModules} from 'react-native';
const {ReactNativeIDnowVideoIdent} = NativeModules;

interface IDnowVideoIdentInterface {
  startVideoIdent(settings: any): Promise<any>;
}

export default ReactNativeIDnowVideoIdent as IDnowVideoIdentInterface;
