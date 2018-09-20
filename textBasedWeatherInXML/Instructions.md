# Instructions

## Weatherunderground API key
You will need to register for an API key for weatherunderground.  I encourage you to do this as soon as possible.  Registering early will allow you to 
start building up "raindrops."  Raindrops are not essential, but they can protect you from getting shut out if you go over your daily limit.

Go to [https//weatherunderground.com](https//weatherunderground.com).  Go over to the far right to **More** and then scroll down to "Weather API for Developers."

You will need to Sin in and create a WeatherUnderground account.  It is free.  I don't recall the exact steps from there, but you should sign up for the lowest level account.  I set mine up as a "Stratus Plan."  I did not have to put in a credit card.

Set up an API key.  You can use something like "Classroom Assignment" as the project name.  You may use Missouri Western or your own website as the Company website.  You do not need a contact phone.

Once you have your API key you will need to add it to your credentials.xml file.


## Imports
I think there will be a popup in the lower right-hand corner of Intellij.  It may prompt you to do imports.  For this assignment I suggest that you select the option to automatically enable imports.

## .gitignore
The .gitignore file contains lists of files that should **not** be uploaded to the
reposoitory.  You will need to modify it so that it stops tracking
the credentials.xml file does not upload.  This will involve
simply removing the # symbol at the start of the #credentials.xml line.
The # marks a comment, so it is currently commented out.

## What to do
Ask the user to type a state and a city.  Fetch the weather
for the city requested.  If there is a blank in the city name you will need
to change it to an underscore (_).

Attempt to fetch the weather for the city and state.  Either return an appropriate
message if the city is not found, or return the weather report.  Return the following fields:
