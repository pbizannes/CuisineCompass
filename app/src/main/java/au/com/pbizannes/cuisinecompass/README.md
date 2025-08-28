# README for Cuisine Compass

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
