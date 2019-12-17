## Lidar Mapping Vechile

# Web API

  Web Api runs on the local host and gathers all the data the raspberry pi sends over wifi. The data being sent to the Api consists of the servo angle which the lidar sits upon. This angle is being swept on the X plane. The lidar does a 360 sweep on the Y and Z axis. The api collects the raw data values and calculated X Y Z coordinates.  
  
# Raspberry Pi

  The raspberry pi handles collecting the lidar data and sending packets of json data do the API.
  
# Processing
[Link to Process!](https://processing.org)

Processing is a very powerful software that allows you to draw graphics. It supports Java, Python, and JavaScript. This project we used Java to do the graphical programming. The as the code draws each frame, it sends a request to the API and retrieves the next API points. 

# Results

<img src="https://media.giphy.com/media/dayFn9op3J2lLw59yq/giphy.gif">
