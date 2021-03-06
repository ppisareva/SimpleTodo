# Pre-work - SimpleTodo

SimpleTodo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Polina Sliusaruck

Time spent: 4 hours spent in total

## User Stories

The following **required** functionality is completed:

* [+] User can **successfully add and remove items** from the todo list
* [+] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [+] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [ +] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [+] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [+] Add support for completion due dates for todo items (and display within listview item)
* [+] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [+] Add support for selecting the priority of each todo item (and display in listview item)
* [+] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [+] Add recyclerView to increase the performance
* [+] Add button in toolbar with adds new task
* [+] Add recyclerView to increase the performance
* [+] Add recyclerView to increase the performance


## Video Walkthrough

Here's a walkthrough of implemented user stories:


![alt text](https://github.com/ppisareva/SimpleTodo/blob/master/demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I don't have much experience with other UI platforms but Android looks very powerful and flexible in terms of building user interfaces. Meanwhile, layout api is slightly complex when we need to create some simple interface, such as showing a static list of text objects: you need to create one activity laytout with some ListView, one list item layout, one activity, create a list adapter, view holder, to bind views etc.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** ArrayAdapter is used to display a list of text values(by default) in Android component ListView. It is a one of the simplest adapters(in compare w/ SimpleAdapter, CursorAdapter, BaseAdapter, RecyclerView.Adapter) and by default it is used with String List of data but you also can use another data by  exntend this class and override a method getView().

If we try to avoid using adapter and list view, we could populate linear layout inside a ScrollView. Within significant amount of list items we could have a performance issue. 

convertView is a custom view, what can be reused to display another list item if the previous item is not visible any more.

## Notes

it was a challenge for me to implement context menu and dialogFragment with recyclerview.

## License

    Copyright polina SLiusarchuk

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
