
## Changelog

**Version 5.73** - Thursday, 30 January 2020

* Fixed file access issue for apps targetting 29
* Fixed issue where files where not uploading on first install

**Version 5.66** - Tuesday, 31 December 2019

* MQTT message encryption.
* Added 2 more paramters in delete group API: updateClientGroupId and resetCount
* RichMessage UI fixes on the Sender side. Support for Image type and multiple buttons type rich messages added.
* isDeepLink option in link type rich messages
* Changed the authority of applozic file provider to applozic.provider in order to avoid conflicts with the same authority.

**Version 5.65** - Monday, 25 November 2019

* Fix notification issues for support groups
* Other crash fixes and optimization

**Version 5.64** - Friday, 18 October 2019

* Added dynamic setting to hide group name edit button
* Added setting in user to hide action messages
* New Rich message support

**Version 5.62** - Friday, 27 September 2019

**Features**
* Updated glide version to 4.9.0
* Added Message search APIs

**Improvements**
* Fixed typing indicator issue.
* Fixed image not sending issue when regex in settings file is empty.
* Fixed video not sending issue.
* Fixed MQTT not connecting when device goes idle for long time with chat open.

**Version 5.60** - Thursday, 5 September 2019

**Features**
* AndroidX migration.
* Option to enable/disable one to one chat.
* Option to set regex to block message that matches the pattern.
* UserId validation before login.
* Option to report a message.

**Fixes**
* Fixed issue where logout was taking too long to respond.
* Other crash fixes.

**Version 5.51**  - Friday, 19 July 2019
* Fixed issue where user email was not updating from the server.

**Version 5.50**  - Tuesday, 16 July 2019

**Features**
* New UI callbacks. You can now register multiple listeners with unique IDs. Then unregister the listeners usig those IDs.
   Registering:
   ```
  AlEventManager.getInstance().registerUIListener("mListener1", this);
   ```
   Unregistering:
    ```
  AlEventManager.getInstance().unregisterUIListener("mListener2");
    ```
* New update user method. The update user now accepts emailId as well. Admin can update the details of other users by passing the userId of the user(Call the below method in a background thread).
```
  User user = new User();
  user.setEmail("email@email.com");
  UserService.getInstance(this).updateUser(user);
```
* Added delete Group method.
* EmailID priority in display name. If user does'nt have display name and has emaild, emailId will be displayed.
* Added contribution doc. Please refer to the contribution doc before you contribute to Applozic SDK.

**Internal changes**
* Added Of-User-Id header for agents
* Added access token header for all authentication types
* updated getConversationList methods and DB calls for agents (Conversation sectioning)

**Fixes**
Fixed crash in personalized messages.

**Version 4.78**  - Thursday, 2 March 2017
   
  - Group of two support 
  - Group member limit in group create and group member add
  - Launch one to one chat in group member name click or profile click 
  - Sending meta data in message by adding settings 
  - Group and profile image upload bug fix
  - Other bug fixes 
 

 **Version 4.76**  - Friday,10 February 2017
   
  - Android N contact sharing bug fix  .
 

 **Version 4.75**  - Wednesday,8 February 2017
   
  - Group Delete support: deleted group will be disabled.
 
 **Version 4.74**  - Sunday, 5 February 2017
   
  - Support for version 7.1 
  - Support for support-library version 25.1.0
  - Chat not syncing bug fix for android 7.1
  
 **Version 4.73**  - Sunday, 5 January 2017
 
 - Support for version 7.0 
 - Group mute
 
   
 **Version 4.71**  - Thursday, 5 January 2017
 
 - Attachments options settings 
 - Restricted words settings 
 
  
   
 **Version 4.64**  - Friday, 18 November 2016
   
  Group info members context menu
 
  Smart messaging with message meta data : Push notification,Archive
  
  Group silent notifications 

  Bug fixes and Improvements
  
### Steps for upgrading from 4.63 to 4.64


**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.64'`


**Version 4.63**  - Tuesday, 25 October 2016
   
  Broadcast messageing 
  
  Smart messaging with message meta data : Hidden messages

  Bug fixes and Improvements


### Steps for upgrading from 4.62 to 4.63


**Step 1: Add the following in your Top-level/Proejct level build.gradle file change the version according to your app**:   

 ```
ext.googlePlayServicesVersion = '9.0.2'
ext.supportLibraryVersion = '23.1.1'
 ```

**Step 2: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.63'`


 **Version 4.62**  - Tuesday, 11 October 2016

  
  Change in Settings config now added json file 
  
  Bug fixes and Improvements


### Steps for upgrading from 4.61 to 4.62


**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.62'`


 **Version 4.61**  - wednesday, 5 October 2016

   Contact list selection added search option ,UI change

   Bug fixes and Improvements


### Steps for upgrading from 4.60 to 4.61


**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.61'`


 **Version 4.60**  - Friday, 30 September 2016
 
 Bug fixes and Improvements
 
  
### Steps for upgrading from 4.59 to 4.60


**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.60'`

 
 **Version 4.59**  - Saturday, 17 September 2016
 
 Bug fixes and Improvements
 
 
###  Steps for upgrading from 4.58 to 4.59

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.59'`
 

 **Version 4.58**  - Thursday, 15 September 2016
 
 Unread count bug fix
 
 Open group issues
 
 Improvements  

 
###  Steps for upgrading from 4.57 to 4.58

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.58'`


 **Version 4.57**  - Wednesday, 7 September 2016
 
 Block and unblock fix
 
 Message list pagination
 
 Message Encryption
 
 Group add,remove,exit,delete group,group icon change meta data supports 
 
 Improvements and bug fixs
 

 
###  Steps for upgrading from 4.56 to 4.57

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.57'`
 
 **Version 4.56**  - Tuesday, 30 August 2016
 
 Improvements and bug fixs
 
 
###  Steps for upgrading from 4.55 to 4.56

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.56'`

 
**Version 4.55**  - Tuesday, 23 August 2016

User block and unblock bug fix

Unread message count fix

Code improvements 

###  Steps for upgrading from 4.53 to 4.55

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.55'`


**Step 2: In Your Android manifest add below code**

```
<service android:name="com.applozic.mobicomkit.api.conversation.ConversationReadService"
          android:exported="false" />

```



 
 
 
**Version 4.53**  - Monday,1 August 2016

Bug fixes and improvement

###  Steps for upgrading from 4.52 to 4.53

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.53'`


**Version 4.52**  - Wednesday,27 July 2016

User and Group image upload and change

Group typing status 

Typing status is moved from bottom to App Bar

User status message change

Bug fixes and performance improvement

###  Steps for upgrading from 4.51 to 4.52

**Step 1: Add the following in your build.gradle dependency**

`compile 'com.applozic.communication.uiwidget:mobicomkitui:4.52'`


**Step 2: In Your Android manifest add below code**

```
 <activity android:name="com.soundcloud.android.crop.CropImageActivity" />

 <service android:name="com.applozic.mobicomkit.api.people.UserIntentService"
          android:exported="false" />

 <service android:name="com.applozic.mobicomkit.api.conversation.ConversationIntentService"
           android:exported="false" />

```

**Version 3.31**

Bug fixes and improvements

**Version 3.30**

 Contact search bug fix
 
 Group name sync changes
 
 Read Count bug fix 
 
**Version 3.29**

User Block

Grid layout for attachment options

Contact Search

Group Change notification
