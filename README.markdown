Recommended Tools for building this project
-------------------------------
  1. [Andorid SDK Starter Package](http://developer.android.com/sdk/index.html) 
  2. [Eclipse IDE](http://www.eclipse.org/downloads/)
  3. [ADT plugin for eclipse](http://developer.android.com/sdk/eclipse-adt.html#installing). 
  
  Note that before you can install or use ADT, you must have compatible versions of both the Eclipse IDE and the Android SDK installed. 

How to Build
------------------------------
    1. Clone this project.
    2. Add ActionBarSherlock to your workspace, see below.
    3. Go to Eclipse -> New -> Android Project -> Create from existing source -> Browse to this project.
    4. Right Click TvTicker, Properties -> Java Build Path -> Add External jar -> add <path_to_tvticker>/lib/CWAC AdapterWrapper -> Finish
    5. Run your project.

Prerequisites
------------------------------
  Requires ActionBarSherlock, Please visit [how to use](http://actionbarsherlock.com/usage.html) for more details.
    Requires the compiled project as a <b>library project</b> in your workspace or project build path.
   
    This sounds weird, I know, but it's really simple. Just follow the instructions below.
    
    1. Clone to [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock) 
    2. Create a new Android project in Eclipse using the ActionBarSherlock/library/ folder as the existing source. Then, in your project properties, add the created project under the ‘Libraries’ section of the ‘Android’ category.
    
    Note: If you were previously using the Android compatability library you must remove its .jar. ActionBarSherlock is built on top of the compatability library and comes bundled with its classes.
    
    
    
  
