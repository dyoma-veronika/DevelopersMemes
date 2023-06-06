# DevelopersMemes
On the main screen of the application, a GIF image is displayed along with its description obtained from the developerslife.ru website.

Below the image, there are two buttons:

One button ("next") loads the next random post (by calling an API method).

The second button allows the user to go back to the previous post, which was saved in the cache after loading.

The structure is as follows: (initially, the back button is inactive, but there is already a post loaded at startup) 
- click on "next" -> a post appears, and the back button becomes active -> 
click "next" again -> a new post appears, and both buttons are active -> 
click back and go back one post (both buttons are active) -> 
click again and go back to the first image (the back button becomes inactive again). 
Now, if you click "next", we should go through the posts that were already loaded, and then, when they are finished, load new ones.

API responses are cached to implement "back" transitions. 
Different data loading states are handled: loading error, loading in progress, and successful loading. 
Stack: JavaRx, Retrofit, and Glide.

Latest             | Top         |  Hot
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/dyoma-veronika/DevelopersLife/blob/master/screenshots/device-2021-01-31-222038.png)   |![](https://github.com/dyoma-veronika/DevelopersLife/blob/master/screenshots/device-2021-01-31-222117.png) |![](https://github.com/dyoma-veronika/DevelopersLife/blob/master/screenshots/device-2021-01-31-222150.png)
