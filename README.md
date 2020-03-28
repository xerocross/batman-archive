# Batman Archive

This project is inspired by Batman's computer, the Batcomputer, that lives 
in his Bat Cave.  Batman is not just a strong vigilante who dresses up like 
a bat and beats up criminals.  He is also the world's greatest detective. 
The Batcomputer is a supercomputer he uses to gather and analyse information
to solve cases.

One of the most basic requirements is an information archive.  Here, we can 
store freeform text data, the archive intelligently indexes the data, and 
there are clever tools in place for querying the archive.

## This Repository

The batman-archive repository (this one) is only the backend portion, which 
is a stand-alone API.  It is still under construction.

## Archive Element



## The API

## background

The specifics of teh API are under constructions, but here is the basic idea.

The unit of information storage is called an Archive Element ("element" 
for short).  An element contains freeform text data.  The backend will
save the posting datetime and the user associated to it.  The backend
will also scan it for keywords and assign keyword tags to it.

Therefore, the API will include a newData endpoint for posting new information.

It will also include an endpoint for querying the database and retrieving a 
list of elements matching the query.

## API usage

### POST /login

POST json data of the form

{
	"username" : [username],
	"password" : [password]
}

### POST /users/sign-up

POST json data of the form

{
	"username" : [username],
	"password" : [password]
}

### POST /archive/new

POST json data of the form

{
	"archiveText" : "This is the first #batcomputer #archive element"
}

### GET /archive/all

returns a list of all archive elements associated to the user

# Front End

There will be a separate repository for the front end project that calls
this API.  