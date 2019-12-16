from flask import Flask, render_template, request
from flask_socketio import SocketIO
import queue
import json, numpy


app = Flask(__name__)

app.config['SECRET_KEY'] = 'vnkdjnfjknfl1232#'
socketio = SocketIO(app)

data = queue.Queue(0)
test = queue.Queue(0)
map = []
i = 0
@app.route('/' , methods=['POST','GET'])
def sessions():
    global data
    global test

    if request.method == 'GET':
        if not data.empty():
            print(data.qsize())
            return data.get()
            
        else:
            return {'status': "p"}
    
    if request.method == 'POST':
        data_R = request.get_json()
        
        # data.put(data_R)

        dic = json.loads(data_R)
        YZcoord = json.loads(dic['lidar'])
        for angle, distance in YZcoord.items():
            y = numpy.math.cos(numpy.math.radians(int(angle)))*(int(distance)/10)
            z = numpy.math.sin(numpy.math.radians(int(angle)))*(int(distance/10))
            x = numpy.math.tan(numpy.math.radians(float(dic['angle']))) * y
            #table.append([x,y,0]) 
            if int(angle) < 13 or int(angle) > 158:
                coordinates = {'status' : "running", 'X': x, 'Y': int(y), 'Z': int(z)}
                if not coordinates in map:
                    data.put(coordinates)
                    map.append(coordinates)
                
        # x = data_R['X']
        # y = data_R['Y']
        # z = data_R['Z']
        
        return data_R
    
    


@app.route('/delete' , methods=['POST','GET'])
def delete():
    global data
    with data.mutex:
        data.queue.clear()

@socketio.on('my event')
def handle_my_custom_event(json, methods=['GET', 'POST']):
    print('received my event: ' + str(json))
    #socketio.emit('my response', json, callback=messageReceived)

if __name__ == '__main__':
    socketio.run(app, debug=True, host="0.0.0.0")
