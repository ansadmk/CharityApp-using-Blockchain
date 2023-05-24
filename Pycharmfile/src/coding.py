import os
from flask import *
from werkzeug.utils import secure_filename

from src.dbconnectionnew import *

app=Flask(__name__)
app.secret_key="8989"


import functools

def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "lid" not in session:
            return render_template('loginpage.html')
        return func()

    return secure_function


@app.route('/logout')
def logout():
    session.clear()
    return redirect('/')


@app.route('/')
def log():
    return render_template('loginpage.html')

@app.route('/login')
def login():
    return render_template('loginpage.html')

@app.route('/logpost',methods=['post'])
def loginpost():
    username = request.form['textfield']
    pwrd = request.form['textfield2']
    qry="SELECT * FROM login WHERE `Username`=%s AND `Password`=%s"
    val=(username,pwrd)
    res=selectone(qry,val)
    if res is None:
        return '''<script> alert("Invalid");window.location="/"</script>'''
    elif res ['Type']=='admin':
        session['lid'] = res['lid']

        return '''<script> alert("welcome to adminhome");window.location="/adminhome"</script>'''
    elif res['Type'] == 'charity':

        qry= "SELECT DATEDIFF(CURDATE(),`Reg_date`) AS DATE FROM `charity organisation` WHERE `proof`='pending' AND `Lid`=%s"
        res2 = selectone(qry,res['lid'])

        if res2 is not None:
            if res2['DATE']>7:
                qry = "DELETE FROM `charity organisation` WHERE `Lid`=%s"
                iud(qry,res['lid'])
                qry = "DELETE FROM `login` WHERE `lid`=%s"
                iud(qry,res['lid'])
                return '''<script> alert("");window.location="/"</script>'''
            else:
                session['lid'] = res['lid']
                return '''<script> alert("welcome to charity home");window.location="/organisationhome"</script>'''
        else:
            session['lid']=res['lid']
            return '''<script> alert("welcome to charity home");window.location="/organisationhome"</script>'''
    else:
        return '''<script> alert("ibvvdw cx");window.location="/"</script>'''



@app.route('/user')
@login_required
def user():
    q = "SELECT * FROM  `user`"
    res = selectall(q)
    return render_template('View user.html',val=res)
@app.route('/user1')
@login_required

def user1():

    q = "SELECT * FROM  `user`"
    res = selectall(q)
    return render_template('View Users.html',val=res)
@app.route('/adminhome')
@login_required
def adminhome():
    return render_template('index1.html')
@app.route('/regi')

def regi():
    return render_template('index.html')

@app.route('/Addcomplaint',methods=['post'])
@login_required

def Addcomplaint():
    return render_template('Add complaint.html')

@app.route('/Addcomplaintpost',methods=['post'])
@login_required

def Addcomplaintpost():
    comp = request.form['textfield']
    qry = "INSERT INTO `complaint` (`lid`,`complaint`,`date`,`reply`) VALUES (%s,%s,curdate(),%s)"
    iud(qry,(session['lid'],comp,'pending'))
    return '''<script> alert("complaint send");window.location="/Send"</script>'''



@app.route('/AddRequest',methods=['post'])
@login_required

def AddRequest():

    return render_template('Add Request.html')

@app.route('/Addrequestpost',methods=['post'])
@login_required

def Addrequestpost():
    comp = request.form['textfield']
    qry="INSERT INTO `request` VALUES(null,%s, %s, curdate(), %s)"
    iud(qry, (session['lid'], comp, 'pending'))
    return '''<script> alert("Request send");window.location="/SendRequest"</script>'''



@app.route('/block')
@login_required

def block():
    qry = "SELECT `charity organisation`.*,`login`.* FROM `charity organisation` JOIN `login` ON  `charity organisation`.Lid=`login`.lid WHERE `login`.Type != 'pending'"
    res = selectall(qry)

    return render_template('Block or unblock charity organisation.html',val=res)

@app.route('/block1')
@login_required

def block1():
    id = request.args.get('id')
    session['oid'] = id
    qry = "update login set type='Blocked' where lid=%s"
    iud(qry, id)
    return '''<script> alert("Blocked");window.location="/block"</script>'''
@app.route('/unblock')
@login_required

def unblock():
    id = request.args.get('id')
    session['oid'] = id
    qry = "update login set type='charity' where lid=%s"
    val=session['oid']
    iud(qry, val)
    return '''<script> alert("unBlocked");window.location="/block"</script>'''


@app.route('/chat')
@login_required

def chat():
    q="SELECT * FROM  `user`"
    res=selectall(q)
    return render_template('chat with user.html',val=res)

@app.route('/organisationhome')
@login_required

def orghome():
    return render_template('index2.html')


@app.route('/Reply',methods=['get','post'])
@login_required

def Reply():
    id=request.args.get('id')
    session['cid']=id
    return render_template('Reply.html')

@app.route('/Replypost',methods=['get','post'])
@login_required

def Replaypost():
    reply = request.form['textfield']
    qry="UPDATE `complaint` SET reply=%s WHERE cid=%s"
    val=(reply,session['cid'])
    iud(qry,val)
    return '''<script> alert("replied");window.location="/ViewUserandCharityComplaints"</script>'''



@app.route('/Send')
@login_required

def send():
    qry="select * from complaint where lid =%s"
    res=selectall2(qry,session['lid'])
    return render_template('Send Complaint and View reply.html',val=res)
@app.route('/sendpost')
@login_required

def sendpost():
    c = request.form['textfield']
    return "okk"

@app.route('/SendRequest')
@login_required

def SendRequest():
    qry="SELECT * FROM `request`"
    res=selectall(qry)
    return render_template('Send Request.html',val=res)

@app.route('/sendRequestpost')
@login_required

def sendRequestpost():
    compi = request.form['textfield']
    return "okk"

@app.route('/UpdateStatus')
@login_required

def UpdateStatus():
    id=request.args.get('id')
    session['rid']=id
    return render_template('Update Status.html')




@app.route('/UpdateStatuspost',methods=['post'])
@login_required

def UpdateStatuspost():
    stts = request.form['textfield']
    q="UPDATE `request` SET `status`=%s WHERE `id`=%s"
    v=(stts,session['rid'])
    iud(q,v)
    return '''<script> alert("updated succesfully");window.location="/SendRequest"</script>'''

@app.route('/Verify')
@login_required
def verify():
    qry = "SELECT `charity organisation`.*,login.*  FROM `charity organisation` JOIN `login` ON  `charity organisation`.Lid=`login`.lid WHERE TYPE='pending'"
    res = selectall(qry)
    return render_template('Verify charity organisation.html',val =res)

@app.route('/accepto')
@login_required
def accepto():
    id=request.args.get('id')
    session['oid']=id
    qry="update login set type='charity' where lid=%s"
    val=(session['oid'])
    iud(qry,val)
    return '''<script> alert("approoved");window.location="/Verify"</script>'''
@app.route('/rejecto')
@login_required
def rejecto():
    id=request.args.get('id')
    qry="update login set type='rejected' where lid=%s"
    iud(qry,id)
    return '''<script> alert("approoved");window.location="/Verify"</script>'''


@app.route('/ViewCharityproof')
@login_required
def ViewCharityproof():
    qry = "SELECT * FROM `charity organisation`"
    res = selectall(qry)
    return render_template('View Charity proof.html',val =res)
@app.route('/ViewDonationposts')
@login_required
def ViewDonationposts():
    qry="SELECT `post`.*,user.*,`Fname`,`Lname`,`Details` ,`Date`FROM `user` JOIN `post` ON `post`.`Lid`=`user`.`lid` where Status='pending'"
    res = selectall(qry)
    print(res)
    return render_template('View Donation posts.html',val=res)

@app.route('/AcceptViewDonationposts')
@login_required
def AcceptViewDonationposts():
    id=request.args.get("id")
    qry="UPDATE `post` SET `Status`='Accepted' WHERE `Id`=%s"
    val=(id)
    iud(qry,val)
    return '''<script> alert("Accepted");window.location="/ViewDonationposts"</script>'''


@app.route('/ViewdonationsofIndividuals')
@login_required
def ViewDonationsofIndividuals():
    qry ="SELECT `donation`.*,`user`.`Fname`,`Lname`,`place`,`post`,`pin`,`email`,`phone`,`request`.`request` FROM `donation` JOIN `request` ON `request`.`id`=`donation`.`Req id` JOIN `user` ON `donation`.`Lid`=`user`.`lid` WHERE `request`.`oid`=%s"
    res = selectall2(qry,session['lid'])
    print(res)
    return render_template('View Donations of Individuals.html', val=res)

@app.route('/ViewResponse')
@login_required
def ViewResponse():
    return render_template('View Response.html')

@app.route('/Viewuser')
@login_required
def Viewuser():
    qry="SELECT * FROM `user`"
    res=selectall(qry)
    return render_template('View user.html',val =res)

@app.route('/ViewUserandCharityComplaints')
@login_required
def ViewUserandCharityComplaints():
    qry="SELECT * FROM `complaint` where reply='pending'"
    res = selectall(qry)
    return render_template('View User and Charity Complaints.html', val=res)

@app.route('/search',methods=['post'])
@login_required
def search():
    type=request.form['select']
    if type == 'user':
      qry="SELECT CONCAT(`user`.Fname, `user`.Lname) AS Name, `complaint`.* FROM `complaint` JOIN `user` ON `user`.lid=`complaint`.lid WHERE `complaint`.reply='pending'"
      res=selectall(qry)
      return render_template('View User and Charity Complaints.html',val=res,type=type)
    else :
        qry = "SELECT `charity organisation`.Name, `complaint`.* FROM `complaint` JOIN `charity organisation` ON `charity organisation`.Lid=`complaint`.lid WHERE `complaint`.reply='pending'"
        res1 = selectall(qry)
        return render_template('View User and Charity Complaints.html', val=res1,type=type)

# /////////////////////////////chat////////////////
@app.route("/chat2")
@login_required
def chatsp():
    pid=request.args.get('uid')
    print(pid,"==============================")
    session['pid']=pid
    qry="SELECT * FROM `user` WHERE `lid`=%s"
    res=selectone(qry,pid)


    print(res)
    qry="    SELECT * FROM `chat` WHERE `from id`=%s AND `lo id`=%s OR `from id`=%s AND `lo id`=%s"


    val=(session['lid'],session['pid'],session['pid'],str(session['lid']))
    res1=selectall2(qry,val)

    print(res)

    fname=res['Fname']
    lname=res['Lname']
    return render_template("chat2.html",data=res1,fname=fname,lname=lname,fr=session['lid'])



@app.route('/send',methods=['post'])
@login_required
def sendchat():
    message=request.form['textarea']
    to_id = session['pid']
    from_id = session['lid']
    qry="insert into chat values(null,%s,%s,%s,CURDATE())"
    val=(from_id,to_id,message)
    iud(qry,val)


    return redirect("chatss")
@app.route("/chatss")
@login_required
def chatss():
    pid=session['pid']
    qry="SELECT * FROM `user` WHERE `lid`=%s"
    res=selectone(qry,pid)
    qry="    SELECT * FROM `chat` WHERE `from id`=%s AND `lo id`=%s OR `from id`=%s AND `lo id`=%s"
    val=(session['lid'],session['pid'],session['pid'],str(session['lid']))
    res1=selectall2(qry,val)
    fname=res['Fname']
    lname=res['Lname']
    return render_template("chat2.html",data=res1,fname=fname,lname=lname,fr=session['lid'])
@app.route('/register')

def register1():
    return render_template('Register form.html')
@app.route('/registerpost',methods=['post'])
def register():
    name = request.form['textfield']
    place= request.form['textfield2']
    post = request.form['textfield3']
    pin  = request.form['textfield4']
    email= request.form['textfield5']
    phone = request.form['textfield6']
    lati=request.form['textfield7']
    long=request.form['textfield8']
    user = request.form['textfield9']
    passw = request.form['textfield10']

    qry1 = "INSERT INTO `login` VALUES(null,%s,%s,'pending')"
    val = (user,passw)
    id = iud(qry1,val)

    qry="INSERT INTO `charity organisation` VALUES(null,%s,%s,%s,%s,%s,%s,%s,%s,%s,'pending',curdate())"
    val = (id,name,place,post,pin,email,phone,lati,long)
    iud(qry,val)
    return '''<script>alert("Successful");window.location="/login"</script>'''
@app.route("/proof")
@login_required
def proof():
    return render_template('proof.html')

@app.route("/proof1",methods=['post'])
@login_required
def proof1():
    proof=request.files['aa']
    im=secure_filename(proof.filename)
    proof.save(os.path.join('static/proof',im))
    qry="UPDATE `charity organisation` SET `proof`=%s WHERE `Lid`=%s"
    val=(im,session['lid'])
    iud(qry,val)
    return '''<script>alert("Successful");window.location="/organisationhome"</script>'''


app.run(debug=True,port=5001)