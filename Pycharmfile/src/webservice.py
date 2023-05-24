import os
from flask import *
from werkzeug.utils import secure_filename

from src.dbconnectionnew import *


import functools
import json
from web3 import Web3, HTTPProvider
app=Flask(__name__)




# truffle development blockchain address
blockchain_address = 'http://127.0.0.1:7545'
# Client instance to interact with the blockchain
web3 = Web3(HTTPProvider(blockchain_address))
# Set the default account (so we don't need to set the "from" for every transaction call)
web3.eth.defaultAccount = web3.eth.accounts[0]

compiled_contract_path = r'C:\Users\dell\PycharmProjects\CharityApp\src\node_modules\.bin\build\contracts\charityinfo.json'
# Deployed contract address (see `migrate` command output: `contract address`)
deployed_contract_address = '0x8E0aA3b75410df2a1344e380b0Ae7b238f9e8f60'



@app.route('/login',methods=['POST'])
def login():
    username=request.form['uname']
    password=request.form['password']
    qry="select * from login where username=%s and password=%s and type='user'"
    val=(username,password)
    res=selectone(qry,val)
    if res is None:
        return jsonify({'task':'invalid'})
    else:
        id= res['lid']
        return jsonify({'task':'success','lid':id})

@app.route('/reg_user',methods=['post'])
def reg_user():
    fname=request.form['fname']
    lname = request.form['lname']
    gender = request.form['gender']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    email = request.form['email']
    phone = request.form['phone']
    user = request.form['username']
    passw = request.form['password']
    qry1 = "INSERT INTO `login` VALUES(null,%s,%s,'user')"
    val = (user,passw)
    id = iud(qry1,val)
    qry="INSERT INTO `user` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    val1=(str(id),fname,lname,gender,place,post,pin,email,phone)
    iud(qry,val1)
    return jsonify({"task":"success"})

@app.route('/adddonation',methods=['post'])

def adddon():
     lid=request.form['lid']
     req=request.form['reqid']
     donation=request.form['Donation']
     qry='INSERT INTO `donation` VALUES(%s,%s,%s,CURDATE(),"pending")'
     val=(str(id),lid,req,donation)
     iud(qry, val)
     return jsonify({"task":"success"})


@app.route('/adddonationposts', methods=['post'])
def adddonposts():
    from datetime import datetime
    lid = request.form['lid']
    details = request.form['details']
    image=request.files['file']
    fff=secure_filename(image.filename)
    image.save(os.path.join('static/photos',fff))

    with open(compiled_contract_path) as file:
        contract_json = json.load(file)  # load contract info as JSON
        contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
    contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
    blocknumber = web3.eth.get_block_number()

    print(blocknumber)
    d=datetime.now().strftime("%Y-%m-%d")
    qry = 'INSERT INTO `post` VALUES(Null,%s,%s,CURDATE(),"pending",%s)'
    val = (lid, details, fff)
    id=iud(qry, val)
    message2 = contract.functions.add_info(blocknumber + 1,int(lid), id,details,d,'pending',fff).transact()
    print(message2)



    return jsonify({"task":"success"})


@app.route('/viewrequestofcharity', methods=['post'])
def viewrequestofcharity():
    qry="SELECT `charity organisation`.`Name`,`charity organisation`.`Phone` ,`request`.* FROM `charity organisation` JOIN `request` ON `charity organisation`.`Lid`=`request`.`oid` where `request`.`status`='pending'"
    res = selectall(qry)
    return jsonify(res)
@app.route('/viewrequeststatus', methods=['post'])
def viewrequeststatus():
    qry="SELECT `charity organisation`.`Name`,`charity organisation`.`Phone` ,`request`.* FROM `charity organisation` JOIN `request` ON `charity organisation`.`Lid`=`request`.`oid` where `request`.`status`='Accepted'"
    res = selectall(qry)
    return jsonify(res)

@app.route('/acceptrequest',methods=['post'])
def acceptrequest():
     oid=request.form['oid']
     req=request.form['req']
     status=request.form['stat']
     qry="INSERT INTO `request` VALUES(%s,%s,CURDATE(),%s)"
     val=(str(id),oid,req,status)
     iud(qry, val)
     return jsonify({"task":"valid"})
@app.route('/sendcomplaint',methods=['post'])
def sendcomplaintandviewreplay():
     lid = request.form['lid']
     complaint = request.form['complaint']
     qry="INSERT INTO `complaint` VALUES(Null,%s,%s,CURDATE(),'pending')"
     val=(str(lid),complaint)
     iud(qry, val)
     return jsonify({"task":"success"})

@app.route('/viewreplayofcomplaint',methods=['post'])
def viewreplayofcomplaint():
    lid=request.form['lid']
    qry1 = "SELECT * FROM `complaint` where lid=%s"
    res = selectall2(qry1,lid)
    print(res,"=================")
    return jsonify(res)
@app.route('/viewcharityorganisation',methods=['post'])
def viewcharityorganisation():
    print(request.form)
    lati =request.form['lati']
    longi = request.form['longi']
    # qry1 = "SELECT * FROM `charity organisation` "
    qry1 = "SELECT `charity organisation`.*, (3959 * ACOS ( COS ( RADIANS(%s) ) * COS( RADIANS( Latitude) ) * COS( RADIANS( Longitude ) - RADIANS(%s) ) + SIN ( RADIANS(%s) ) * SIN( RADIANS( Latitude ) ))) AS user_distance FROM `charity organisation`  HAVING user_distance < 31.068"
    res = selectall2(qry1,(lati,longi,lati))
    return jsonify(res)
@app.route('/viewallcharityorganisation',methods=['post'])
def viewallcharityorganisation():
    # qry1 = "SELECT * FROM `charity organisation` "
    qry1 = "SELECT * FROM `charity organisation`"
    res = selectall(qry1)
    return jsonify(res)

@app.route('/donation',methods=['post'])
def donation():
    lid = request.form['lid']
    print(lid, "=================")
    qry1 = "SELECT * FROM `post` WHERE Lid=%s"
    res = selectall2(qry1,lid)
    print(res, "=================")
    return jsonify(res)

@app.route('/chat',methods=['post'])
def chat():
    qry="SELECT * FROM `charity organisation`"
    res=selectall(qry)
    return jsonify(res)

@app.route('/acceptrequest1',methods=['post'])
def acceptrequest1():
    id = request.form['id']

    qry1 = "UPDATE `request` SET `status`='Accepted' WHERE `id`=%s"
    iud(qry1,id)
    return jsonify({"task": "valid"})
@app.route('/rejectrequest',methods=['post'])
def rejectrequest():
    id = request.form['id']

    qry1 = "UPDATE `request` SET `status`='pending' WHERE `id`=%s"
    iud(qry1,id)
    return jsonify({"task": "valid"})


# ////////////chat

@app.route('/in_message2',methods=['post'])
def in_message():
    print(request.form)
    fromid = request.form['fid']
    print("fromid",fromid)
    toid = request.form['toid']
    print("toid",toid)
    message=request.form['msg']
    print("msg",message)
    qry = "INSERT INTO `chat` VALUES(NULL,%s,%s,%s,CURDATE())"
    value = (fromid, toid, message)
    print("pppppppppppppppppp")
    print(value)
    iud(qry, value)
    return jsonify(status='send')

@app.route('/view_message2',methods=['post'])
def view_message2():
    print("wwwwwwwwwwwwwwww")
    print(request.form)
    fromid=request.form['fid']
    print(fromid)
    toid=request.form['toid']
    print(toid)
    lmid = request.form['lastmsgid']
    print("msgggggggggggggggggggggg"+lmid)
    sen_res = []
    # qry="SELECT * FROM chat WHERE (fromid=%s AND toid=%s) OR (fromid=%s AND toid=%s) ORDER BY DATE ASC"
    # qry="SELECT `from id`,`date`,`id`,chat FROM `chat` WHERE `id`>%s AND ((`lo id`=%s AND  `from id`=%s) OR (`lo id`=%s AND `from id`=%s))  ORDER BY `id` ASC"
    qry="    SELECT `from id` as from_id,`chat`,`date`,`id` FROM `chat` WHERE `id`>%s AND ((`lo id`=%s AND `from id`=%s)OR (`lo id`=%s AND `from id`=%s)) ORDER BY `id` ASC"
    # print("SELECT `from id`,`date`,`id`,chat FROM `chat` WHERE `id`>%s AND ((`lo id`=%s AND  `from id`=%s) OR (`lo id`=%s AND `from id`=%s))  ORDER BY `id` ASC")
    print("    SELECT `from id`,`chat`,`date`,`id` FROM `chat` WHERE `id`>%s AND ((`lo id`=%s AND `from id`=%s)OR (`lo id`=%s AND `from id`=%s)) ORDER BY `id` ASC")
    # print("SELECT `fromid`,`message`,`date`,`msgid` FROM `chat` WHERE `msgid`>%s AND ((`toid`=%s AND  `fromid`=%s) OR (`toid`=%s AND `fromid`=%s)  )  ORDER BY msgid ASC")
    val=(str(lmid),str(toid),str(fromid),str(fromid),str(toid))
    print("fffffffffffff",val)
    res = selectall2(qry,val)
    print("resullllllllllll")
    print(res)
    if res is not None:
        return jsonify(status='ok', res1=res)
    else:
        return jsonify(status='not found')

@app.route('/delete',methods=['post'])
def delete():
    id = request.form['id']
    qry1 = "DELETE  from `post` WHERE `Id`=%s"
    iud(qry1, id)
    return jsonify({"task": "valid"})


app.run(host='0.0.0.0',port=5000)

# with open(compiled_contract_path) as file:
#     contract_json = json.load(file)  # load contract info as JSON
#     contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
# contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
# blocknumber = web3.eth.get_block_number()
#
# print(blocknumber)
#
#
# # message2 = contract.functions.add_info(blocknumber + 1, 1, 1,"details","2023-03-20",'completed',"img").transact()
# # print(message2)
# for i in range(blocknumber, 4, -1):
#     a = web3.eth.get_transaction_by_block(i, 0)
#     decoded_input = contract.decode_function_input(a['input'])
#     print(decoded_input)