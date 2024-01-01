import React from 'react';
import {Button} from 'react-native';
import ReactNativeIDnowVideoIdent from './ReactNativeIDnowModule/IDnowVideoIdentModule';

function NewModuleButton(): JSX.Element {
  //const [err, setErr] = useState('');
  //const [image, setImage] = useState<string | undefined>();

  const onPress = async () => {
    try {
      await ReactNativeIDnowVideoIdent.startVideoIdent({
        token: 'KTS-BRADF',
        env: 'LIVE',
      });
    } catch (e) {
      console.error(e);
    }
  };

  //const pickImage = () => {
  //  ImagePickerModule.pickImage()
  //    .then((uri: string) => setImage(uri))
  //    .catch(error => setErr(`error: ${JSON.stringify(error)}`));
  //};

  return (
    <>
      <Button
        title="Click to invoke your native module!"
        color="#841584"
        onPress={onPress}
      />
    </>
  );
}

export default NewModuleButton;
