#import "RCTReactNativeIdnowVideoident.h"
#import <React/RCTLog.h>
#import <IDnowSDK/IDnowSDK.h>

@implementation RCTReactNativeIdnowVideoident

RCT_EXPORT_MODULE(ReactNativeIdnowVideoIdent);

RCT_EXPORT_METHOD(startVideoIdent:(NSDictionary *)settings
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
  UIViewController *rootViewController = [[[UIApplication sharedApplication] delegate] window].rootViewController;
  

  IDnowAppearance *appearance = [IDnowAppearance sharedAppearance];
  UIColor *coinbaseColor = [UIColor colorWithRed:0.0f/255.0f green:82.0f/255.0f blue:255.0f/255.0f alpha:1.0f];
  UIColor *coinbaseGreen = [UIColor colorWithRed:9.0f/255.0f green:133.0f/255.0f blue:81.0f/255.0f alpha:1.0f];
  UIColor *coinbaseGray = [UIColor colorWithRed:91.0f/255.0f green:97.0f/255.0f blue:110.0f/255.0f alpha:1.0f];
  UIColor *coinbaseRed = [UIColor colorWithRed:207.0f/255.0f green:32.0f/255.0f blue:47.0f/255.0f alpha:1.0f];

  appearance.primaryBrandColor = coinbaseColor;
  appearance.backgroundColor = [UIColor whiteColor];
  appearance.defaultTextColor = [UIColor blackColor];
  appearance.textFieldColor = coinbaseGray;
  appearance.proceedButtonTextColor = [UIColor whiteColor];
  appearance.proceedButtonBackgroundColor = coinbaseColor;
  appearance.checkIconColor = coinbaseColor;
  appearance.cqcOuterRingColor = coinbaseGray;
  appearance.cqcDefaultInnerRingColor = [UIColor whiteColor];
  appearance.cqcPoorQualityInnerColor = coinbaseRed;
  appearance.cqcExcellentQualityInnerColor = coinbaseColor;
  appearance.failureColor = coinbaseRed;
  appearance.successColor = coinbaseColor;
  appearance.mode = IDNOW_MODE_LIGHT;


  IDnowSettings *idnowSettings = [IDnowSettings settingsWithCompanyID:@"solarisbankvideoidentcoinbase"];
  idnowSettings.transactionToken = settings[@"transactionToken"];
  idnowSettings.forceModalPresentation = true;
  idnowSettings.forceErrorSuccessScreen = false;
  idnowSettings.showErrorSuccessScreen = false;
  idnowSettings.showVideoOverviewCheck = false;
  if (@available(iOS 14, *)) {
    rootViewController.navigationItem.backButtonTitle = @"Back";
    rootViewController.navigationItem.backButtonDisplayMode = UINavigationItemBackButtonDisplayModeDefault;
  }
  
  idnowSettings.userInterfaceLanguage = @"de";
  if (settings[@"language"] != nil) {
    idnowSettings.userInterfaceLanguage = settings[@"language"];
  }



  IDnowController *idnowController  = [[IDnowController alloc] initWithSettings:idnowSettings];

  [idnowController initializeWithCompletionBlock:^(BOOL success, NSError * _Nullable error, BOOL canceledByUser) {
    NSMutableDictionary *identificationResult = [[NSMutableDictionary alloc] init];
    NSString* resultCode = [self getResultCode:success canceledByUser:canceledByUser];
    [identificationResult setValue:resultCode forKey:@"resultCode"];
    if (success) {
      [idnowController startIdentificationFromViewController:rootViewController withCompletionBlock:^(BOOL success, NSError * _Nullable error, BOOL canceledByUser) {
        NSString* resultCode = [self getResultCode:success canceledByUser:canceledByUser];
        [identificationResult setValue:resultCode forKey:@"resultCode"];
        if (success) {
          resolve(@[identificationResult]);
          return;
        } else {
          [identificationResult setValue:error.localizedDescription forKey:@"errorMessage"];
          RCTLogInfo(@"ReactNativeIdnowVideoIdentError: %@", error.localizedDescription);
          reject(@"error_during_videoident", error.localizedDescription, error);
          return;
        }
      }];
    } else {
      RCTLogInfo(@"ReactNativeIdnowVideoIdentError: %@", error.localizedDescription);
      reject(@"error_starting_videoident", error.localizedDescription, error);
      return;
    }
  }];
}


- (NSString *)getResultCode: (BOOL)success canceledByUser:(BOOL)canceledByUser {
  if ( success ) {
    return @"SUCCESS";
  }
  if ( canceledByUser) {
    return @"CANCEL";
  }
  return @"FAILED";
}

@end
