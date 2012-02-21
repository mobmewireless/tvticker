Recommended Tools for building this project
-------------------------------
  1. [Andorid SDK Starter Package](http://developer.android.com/sdk/index.html) 
  
  2. [Eclipse IDE](http://www.eclipse.org/downloads/)
  
  3. [ADT plugin for eclipse](http://developer.android.com/sdk/eclipse-adt.html#installing). 
  
  Note that before you can install or use ADT, you must have compatible versions of both the Eclipse IDE and the Android SDK installed. 

### How to Build
   
1. Clone this project.
2. Add ActionBarSherlock to your workspace, see below.
3. Clone TV Ticker/mobile from its GIT url.
3. Go to File -> New -> Android Project. Click "Create from existing source" and use the cloned url of the mobile repo. Use "TV Ticker" for the project name. Click next. Use Android 3.2, and then click Finish.
4. Right Click TvTicker, Properties -> Java Build Path -> Libraries -> Add External jar -> add <path_to_tvticker>/lib/CWAC AdapterWrapper -> Finish
5. Right Click TvTicker, Properties -> Android. From Library, remove references to JakeWharton-ActionBarSherlock and add correct reference by clicking Add and selecting ActionBarSherlock.
6. Run your project.


### Action Bar Sherlock
  
This requires ActionBarSherlock, Please visit [how to use](http://actionbarsherlock.com/usage.html) for more details.
    
1. Clone [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock)     
2. 
  a. Click File -> New Project -> Android Project. Click "Create from existing source" and use the library/ folder inside the cloned project.
  b. For Project name, using ActionBarSherlock
  c. Click Next
  d. Choose "Android 3.2" as the target and click Finish



