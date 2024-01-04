Title: EquipMe

Description:
EquipMe is a tool management app inspired by my grandfather and created for COMP 2160 (Mobile 1) at Thompson Rivers Univerity
during the Winter Semester 2023.
The app is intended to catalogue various different pieces of equipment my grandfather uses, storing information like oil type,
fuel type, maintenance log, and common parts.

Languages Used:
The app was created in Android Studio using Java and XML, it also includes a simple database using SQLite.

Key Learning Points:
Android Studio basics. This was a capstone project for the course which taught the basics of Android development like views and 
fragments. This project made extensive use of recycler views which were created specifically for each use case. Appart from other 
basic features of Android like textviews and buttons, the app uses shared preferences to keep user login.

SQLite database. Databases were not part of course, however I decided that a database was crucial for the function of this app.
This was my first time using SQLite and databases, as such, the database is rough around the edges, though functional. The 
creation of this database was entirely self-taught, so it was a good oportunity to learn SQL-based languages.

Challenges:
The main challenge with this project was creating and implimenting a database with SQLite. This was my first time using and SQL-based
language and was entirely self-taught, so it was a challenge. The database is created and hosted entirely on the device running the
app by using SQLite. I think this choice was a mistake as it would have been much more effective to have created an online database.
The other major challenge with the database was storing user-uploaded images. I had initially tried to store images in the database
using bitmaps but I was never able to get this to work. I ended up storing hyperlinks to online images instead of allowing 
user-uploaded images, which presents the issue as the images are not hosted or owned by me.

Areas of Possible Improvement:
The main way to improve this project would be to change the database to an online version and allow user-uploaded images.
When testing the app on an Android device, I also noticed reliablity issues including crashing and slow performance, this could have
been caused by poor device performance as emulators worked fine, but the app could also be optimized. 
The last way to improve the app would be to create formats for different screen sizes.
