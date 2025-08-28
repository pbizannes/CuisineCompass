# README for Cuisine Compass

Displays list of restaurants, with accompanying detail screen.

Restaurant cards are sorted by best deals first.

Search filter functionality includes filter by partial name, and cuisines

## Development notes


Initially I created the project using Android Studio - File / New / New Project / Empty Activity

Then I setup the basic structure with presentation, domain, and data layers, and di for hilt. This
is for a clean separation of the various parts of the app.

I created the CoroutinesModule (I probably won't use all those dispatchers, unless this project is
extended, but just in case), RetrofitModule and RestaurantService.

I initially created models simply by pasting the output from the endpoint into Gemini and asking it
"Generate models for the given JSON". I made these parcelable, so I get the benefits of the material3
list-detail panel which requires gives you a list/detail screen and correctly remembers between
orientation changes by parceling objects. 


I added a use case to get the restaurant list and sort by the best deal. There is also sorting
done in the Mapper so the best deal is shown in the restaurant list and is sorted in the
retaurant details.

It shows on my phone in Dark Theme, but this can be changed on your phone to make it look more like
the screenshots.

Added some tests for view model and repository, with a little help from AI

Note that the base of the url for loading restaurants is defined in app/build.gradle.kts



### Things Still to be Done

- Many of the strings are hard-coded directly and not used from the resources.
- This was done quickly. I might revisit using retrofit if I was looking at this again.
- I would add more tests with more time. Eg: a test for the use case.
- 
