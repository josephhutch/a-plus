from flask import Flask, jsonify, send_file, request
import fakeDB

application = Flask(__name__)


@application.route('/todo/api/v1.0/events', methods=['GET'])
def get_events():
    return jsonify({'events': fakeDB.events})

@application.route('/todo/api/v1.0/users', methods=['GET'])
def get_users():
    return jsonify({'users': fakeDB.users})

@application.route('/todo/api/v1.0/user', methods=['GET'])
def get_user():
    if request.args.get('id') == '1':
        return jsonify({'user': fakeDB.users[0]})
    if request.args.get('id') == '2':
        return jsonify({'user': fakeDB.users[1]})
    if request.args.get('id') == '3':
        return jsonify({'user': fakeDB.users[2]})
    if request.args.get('id') == '4':
        return jsonify({'user': fakeDB.users[3]})
    if request.args.get('id') == '5':
        return jsonify({'user': fakeDB.users[4]})
    if request.args.get('id') == '6':
        return jsonify({'user': fakeDB.users[5]})
    if request.args.get('id') == '7':
        return jsonify({'user': fakeDB.users[6]})
    if request.args.get('id') == '8':
        return jsonify({'user': fakeDB.users[7]})
    if request.args.get('id') == '9':
        return jsonify({'user': fakeDB.users[8]})
    if request.args.get('id') == '10':
        return jsonify({'user': fakeDB.users[9]})
    if request.args.get('id') == '11':
        return jsonify({'user': fakeDB.users[10]})
    if request.args.get('id') == '12':
        return jsonify({'user': fakeDB.users[11]})


@application.route('/get_image')
def get_image():
    filename = 'images/' + request.args.get('type') + '.jpg'
    return send_file(filename, mimetype='image/gif')

# run the app.
if __name__ == "__main__":
    # Setting debug to True enables debug output. This line should be
    # removed before deploying a production app.
    application.debug = True
    application.run()
