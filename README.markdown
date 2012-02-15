### Required Reading

  1. [Andorid SDK Starter Package](http://developer.android.com/sdk/index.html) 
  2. [Eclipse IDE](http://www.eclipse.org/downloads/)
  3. [ADT plugin for eclipse](http://developer.android.com/sdk/eclipse-adt.html#installing). 
  
  Note that before you can install or use ADT, you must have compatible versions of both the Eclipse IDE and the Android SDK installed. 

### How to Build
   
1. Clone this project.
2. Add ActionBarSherlock to your workspace, see below.
3. Clone TV Ticker/mobile from its GIT url.
4. 
    1. Go to File -> New -> Android Project. 
    2. Click "Create from existing source" and use the cloned url of the mobile repo. 
    3. Use "TV Ticker" for the project name. 
    4. Click next. Use Android 3.2, and then click Finish.
5. Right Click TvTicker, Properties -> Java Build Path -> Libraries -> Add External jar -> add <path_to_tvticker>/lib/CWAC AdapterWrapper -> Finish
6. Right Click TvTicker, Properties -> Android. From Library, remove references to JakeWharton-ActionBarSherlock and add correct reference by clicking Add and selecting ActionBarSherlock.
7. Run your project.

### Action Bar Sherlock
  
This requires ActionBarSherlock, Please visit [how to use](http://actionbarsherlock.com/usage.html) for more details.
    
1. Clone [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock)     
2. Add it to Eclipse:
    1. Click File -> New Project -> Android Project. Click "Create from existing source" and use the library/ folder inside the cloned project.
    2. For Project name, using ActionBarSherlock
    3. Click Next
    4. Choose "Android 3.2" as the target and click Finish



