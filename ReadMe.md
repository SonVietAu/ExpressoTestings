*** Expresso Testings

**** Aim

To understand all the features available from Expresso.

**** Method and Result
1 The first feature to use will be ViewMatcher 'allOf'. The function 'allOf' is able to pinpoint a precise view should views share common features such as same id. The pinpointing is done with more condition parameters such as 'withText(...)' and 'not(withText(...))'. 

Result
    The function 'allOf' comes from Hamcrest and had to be imported into this project. 

1_1 To simulate a AmbiguousViewMatcherException, two 'sub' layout files, sub_view1 and sub_view2 was 'include' in the main_fragment layout. The two subviews are made of a text view with an id of 'aTextView'. In the test, 'onView' and 'withId' were used to locate the two subview with an expectation that the AmbiguousViewMatcherException would occur. 

Result
    The use of 'onView' and 'withId' did not throw the expected AmbiguousViewMatcherException, instead, NoMatchingViewException was thrown. To ensure that 'withId' work as expected, it was used on the project original id R.id.message and it is successful. The 'include' of the two subviews was not able to show the failing of 'withId' to throw the AmbiguousViewMatcherException. With no other known ways of showing such throw, this feature will be left open. 
    
1_2 The AmbiguousViewMatcherException can also be thrown when 'the specific view may not have an R.id'. To simulate this, the two 'Same Same' text views were added to the main_fragment layout. To simulate the use of a second condition, the two 'Diff Diff' text views was added and they correspond to the colors of the 'Same Same' views. 
    
Result
    The use of 'onView' with only 'withText("Same Same")' thrown the expected AmbiguousViewMatcherException with the expected exception message. 
    The use of 'not(withText("Same Same"))"' also thrown the expected AmbiguousViewMatcherException and message. A printStackTrace shown that almost all of the views in the hierarchy matched such condition. 
    Adding 'allOf' and 'hasTextColor' allowed the test to pinpoint the correct text view and no AmbiguousViewMatcherException was thrown. 
    
1_3 Espresso allow for testing of ViewActions. This section will test 'click' and 'typeText'. The test of 'click' will increment and show a value. The test for 'typeText' will multiply an integer input by 2 while also display an error for any non-integer typing. 

Result
    The click() and typeText() functions work as expected, the integer was multiplied by two and an error was shown for non-integer typing.
    
1_4 Espresso makes testing of adapter views possible with onData(). To simulate onData() usage, a TextView from a ListView will be clicked and the clicked text will be displayed in another TextView that is outside of the ListView. 

Result
    onData() uses matchers that operate on data of adaptive views rather than matchers that operate on views. However, onData() will accept matchers that operate on views as argument and this can lead to confusing errors that will not be seen at compiling time. Espresso or Hamcrest should make the distinction. The used of withId() as parameter to onData() results in "RuntimeException: No data found matching: with id is <...>" when withId() should not have been accepted. 

1_5 Using onView() to look for a specific view that fail and an adaptive view is being used, "Espresso warns users about presence of AdapterView widgets" and the exception message will include "a list of the adapter views". To show such feature, the view from getView() of DaysAdapter is given a tag and an id. Using onView withId() should throw such exception. 

2_1 Simulate hasSibling() "matcher to narrow down your selection". 

Result
    Matching with only withText("30") did not narrow down to a specific view and an AmbiguousViewMatcherException was thrown. Adding hasSibling() successfully narrowed down to the specific view.

2_2 Simulate matching "a view that is inside an action bar". 

Requirement
    Have at least an action bar and menu. Context menu is also easy to add and hence there is one as well. 
    
Result
    Espresso was only able to find action menu 'firstMItem' using withId(), the item has 'showAsAction' 'always'. The function withId() failed to find menuItem in the overflowing menu. However, withText() found menuItems the overflowing. Closer inspection found the menuItems in the overflowing have on-the-fly ids rather than the ids that was named in the menu xml setup file. However, after invoking perform(click()), onOptionsItemSelected() comparison of id from the MenuItem parameter matches that of the named id in the xml setup file, that is the on-the-fly ids is the named id but withId() failed to match them for some reasons. 

2_3 Simulate matching a view that is inside a floating and an action mode contextual action bars. 

Result
    Floating contextual action bar worked as expected. After opening, views can be found using withText(). Once click(), the view menuResultTV was updated as specified. Just as with the normal action bar, withId() failed to find the specific MenuItem. 
    Action mode contextual action bar worked in the same manner as the floating. The function withText() found the correct MenuItem while withId() failed. One different is that the action mode requires openActionBarOverflowOrOptionsMenu() to be call to open the overflowing part. 

2_4 Simulate asserting that a view is displayed and not displayed. 

Result
    Simple used of the functions onView(), withId(), click() and isDisplayed() suffice to assert a view is displayed or not. 

2_5 Simulate asserting that a view is present and not present. 
    
Result
    With only doesNotExist() available, checking for a present of a view required the double negative of not(doesNotExist()) in a matches(). Checking for not being present is a simple use of the function doesNotExist().

2_6 Simulate asserting that a "data item is not in an adapter". 

Result
    To assert that a data item is not in an adapter, a function to create a custom Matcher was needed and built. The custom Matcher check that a view is an AdapterView and that the adapter contain the expected value. With the function and custom Matcher, the assertion became simple. 

2_7 Simulate handling a custom failure. A custom failure is an extension of FailureHandler. 

Result
    Setting up a custom FailureHandler is very simple by subclassing the FailureHandler and calling setFailureHandler(). Espresso already have very good debugging information within the 'error' and 'viewMatcher' parameters to handle(). The usefulness of such customisation would come in some yet to be discovered very specific circumstances. 
    One recommendation from Espresso is to take screenshot. This is like a whole new showcase project of its own and will be left as a TODO. 
    
3_1 Enable accessibility check and suppressing check on views

Result
    Accessibility is simply activated with AccessibilityChecks.enable() and a perform(click()). Calling setRunChecksFromRootView(true) will check every views rather than just the view that perform(click()) was activated on. Calling setSuppressingResultMatcher(resultMatcher) will suppress check on views that satisfy the 'resultMatcher'. 
    
4_1 Match data using a custom view matcher

Result
    A custom matcher was built to check that a text is displayed within the hierarchy of a ConstraintLayout. Without the custom matcher, the testing would have to match to children of the constraint and then to the text. As such, custom matchers can handle composite views. 

4_2 Match data to a specific child view

Result
    The function onChildView() locate a specific child view that matches its ViewMatcher parameter. As such, the custom view matcher of 4_1 can be replaced with this function. This function is also more effective as it is not specific to any particular parent view but can find child view within any layout views.

4_3 Simulate matching a header and footer in a ListView

Result
    To match headers and footers in testing require a data parameter to addFooterView() and addHeaderView() but such data may not be of any use in the application. Along with ListView being obsoleted by RecyclerView, this exercise will intentionally un-worked. 

4_4 Use scrollTo()

Result
    The function scrollTo() works as it stated, a scroll to the last (Smarch) removed the first member (Jan) with an attempt to click on Jan resulted in an error. 

4_5 Use scrollToHolder()

Result
    Scrolling to specific holder requires holder to have an id that is not of much use in a RecyclerView as holders get recycled and can not be sure that any particular holder is on screen. As such, his exercise will be intentionally left uncompleted.

4_6 Use scrollToPosition()

Result
    Scroll to a specific position provides little information about the data and that the function can take any arbitrary integer like -130 for a set of 13 members makes scrollToPosition() not very useful. In the exercise, after scrolling to a nonsensical position and perform(..., click()), it is not possible to determine where in the RecyclerView is showing. Hence can not determine which ViewHolder to interact with and no further result can be obtained. 

4_7 Use actionOnHolderItem(), actionOnItem() and actionOnItemAtPosition()

Result
    These functions do not add any extra value to Espresso library as they can easily be handled with the scrollTo...() functions and espresso ViewActions. 

Multi-process will not be included as it is of little use in Android Development. 

1_W Test 'scrollTo' and 'click' that will display the clicked month. 

1_X For "target view is inside an AdapterView—such as ListView, GridView, or Spinner—the onView() method might not work", "use onData() instead". Will this work for RecyclerView with its own Adapter?

TODO: Espresso uses an "almost always good enough" "heuristic to guess which Window you intend to interact with" in a multiple windows scenario. But what is "multiple windows"? Will need more research into multiple windows and go through https://github.com/android/android-test/blob/7e834ce37faf52f2a65a73b0a6d83ab148707cbb/testapps/ui_testapp/javatests/androidx/test/ui/app/MultipleWindowTest.java. 

