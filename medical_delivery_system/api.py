from flask import *
from database import *
import demjson
import uuid

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)

@api.route('/usermanagefeedback')
def usermanagefeedback():
	data={}
	lid=request.args['lid']
	complaint=request.args['complaint']
	q="insert into feedback values(null,(select user_id from users where login_id='%s'),'%s',curdate())"%(lid,complaint)
	insert(q)
	data['status']="success"
	data['method']="usermanagefeedback"
	return demjson.encode(data)


@api.route('/userviewfeedback')
def userviewfeedback():
	data={}
	lid=request.args['lid']
	q="select * from feedback where user_id=(select user_id from users where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewfeedback"
	return demjson.encode(data)


@api.route('/userregister')
def userregister():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	email=request.args['email']
	phone=request.args['phone']
	dob=request.args['dob']
	district=request.args['district']
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		q="insert into login values(null,'%s','%s','user')"%(username,password)
		id=insert(q)
		q="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,dob,phone,email,place,district)
		insert(q)
		data['status']="success"
	return demjson.encode(data)



@api.route('/doctorregister')
def doctorregister():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	email=request.args['email']
	phone=request.args['phone']
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		q="insert into login values(null,'%s','%s','pending')"%(username,password)
		id=insert(q)
		q="insert into doctors values(null,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
		insert(q)
		data['status']="success"
	return demjson.encode(data)


@api.route('/deliveryregister')
def deliveryregister():
	data={}
	name=request.args['name']
	lname=request.args['lname']
	email=request.args['email']
	phone=request.args['phone']
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		q="insert into login values(null,'%s','%s','delivery')"%(username,password)
		id=insert(q)
		q="insert into deliveryboy values(null,'%s','%s','%s','%s','%s')"%(id,name,lname,phone,email)
		insert(q)
		data['status']="success"
	return demjson.encode(data)






@api.route('/deliveryboyvieworderdispatched')
def deliveryboyvieworderdispatched():
	data={}
	lid=request.args['lid']
	q="select *,`uploadprescription`.`date` AS pdate from delivery inner join uploadprescription using(prescription_id) inner join users using(user_id) where boy_id=(select boy_id from deliveryboy where login_id='%s') and `status`='dispatched'"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="deliveryboyvieworderdispatched"
	return demjson.encode(data)

@api.route('/deliveryboyvieworderpickup')
def deliveryboyvieworderpickup():
	data={}
	lid=request.args['lid']
	q="select *,`uploadprescription`.`date` AS pdate from delivery inner join uploadprescription using(prescription_id) inner join users using(user_id) where boy_id=(select boy_id from deliveryboy where login_id='%s') and `status`='pickup'"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="deliveryboyvieworderpickup"
	return demjson.encode(data)


@api.route('/deliveryboyupdatestatustopickup')
def deliveryboyupdatestatustopickup():
	data={}
	lid=request.args['lid']
	pid=request.args['pid']
	q="update uploadprescription set status='pickup' where prescription_id='%s'"%(pid)
	update(q)
	q="update delivery set date=NOW() where prescription_id='%s'"%(pid)
	update(q)
	data['status']="success"
	data['method']="deliveryboyupdatestatustopickup"
	return demjson.encode(data)


@api.route('/deliveryboyupdatestatustodeliverd')
def deliveryboyupdatestatustodeliverd():
	data={}
	lid=request.args['lid']
	pid=request.args['pid']
	q="update uploadprescription set status='delivered' where prescription_id='%s'"%(pid)
	update(q)
	q="update delivery set date=NOW() where prescription_id='%s'"%(pid)
	update(q)
	data['status']="success"
	data['method']="deliveryboyupdatestatustodeliverd"
	return demjson.encode(data)






@api.route('/userviewmedicalshop')
def userviewmedicalshop():
	data={}
	q="SELECT * FROM `medicalshop`"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)




@api.route('/userview_doctors')
def userview_doctors():
	data={}
	q="SELECT * FROM `doctors` inner join login using (login_id) where usertype='doctor'"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)


@api.route('/useruploadprescriptions',methods=['get','post'])
def useruploadprescriptions():
	data={}
	sid=request.form['sid']
	lid=request.form['logid']
	image=request.files['image']
	path="static/"+str(uuid.uuid4())+image.filename
	image.save(path)
	q="insert into uploadprescription values(null,'%s',(select user_id from users where login_id='%s'),'%s','',curdate(),'pending')"%(sid,lid,path)
	insert(q)
	data['status']="success"
	data['method']="useruploadprescriptions"
	return demjson.encode(data)



@api.route('/userviewuploadedfilesprescription')
def userviewuploadedfilesprescription():
	data={}
	lid=request.args['lid']
	sid=request.args['sid']
	q="select * from uploadprescription where user_id=(select user_id from users where login_id='%s') and medicalshop_id='%s'"%(lid,sid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewuploadedfilesprescription"
	return demjson.encode(data)



@api.route('/userviewuploadedmedicaldetails')
def userviewuploadedmedicaldetails():
	data={}
	pid=request.args['pid']
	q="SELECT *,`medicinedetails`.`quantity` AS mquantity,`medicinedetails`.`rate` AS mrate,`medicinedetails`.`total` AS mtotal FROM `medicinedetails` INNER JOIN `uploadprescription` USING(prescription_id) INNER JOIN `medicines` USING(medicine_id) INNER JOIN `type` USING(type_id) WHERE prescription_id='%s'"%(pid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewuploadedmedicaldetails"
	return demjson.encode(data)


@api.route('/useracceptmedicine')
def useracceptmedicine():
	data={}
	pid=request.args['pid']
	q="update `uploadprescription` set status='accept' where prescription_id='%s'"%(pid)
	update(q)
	data['status']="success"
	data['method']="useracceptmedicine"
	return demjson.encode(data)



@api.route('/userrejectmedicine')
def userrejectmedicine():
	data={}
	pid=request.args['pid']
	q="update `uploadprescription` set status='reject' where prescription_id='%s'"%(pid)
	update(q)
	data['status']="success"
	data['method']="userrejectmedicine"
	return demjson.encode(data)


@api.route('/usermakepayment')
def usermakepayment():
	data={}
	mid=request.args['mid']
	pid=request.args['pid']
	quantity=request.args['quantity']
	amount=request.args['amount']
	q="insert into payment values(null,'%s','%s',curdate())"%(pid,amount)
	insert(q)
	q="update `uploadprescription` set status='paid' where prescription_id='%s'"%(pid)
	update(q)
	q="update medicines set quantity=quantity-'%s' where medicine_id='%s'"%(quantity,mid)
	update(q)
	data['status']="success"
	return demjson.encode(data)



@api.route('/usermakeappointments')
def usermakeappointments():
	data={}
	did=request.args['did']
	date=request.args['date']
	time=request.args['time']
	lid=request.args['lid']
	q="insert into appoinments values(null,'%s',(select user_id from users where login_id='%s'),'%s','%s','pending')"%(did,lid,date,time)
	insert(q)
	data['status']="success"
	return demjson.encode(data)




@api.route('/userviewappoinments')
def userviewappoinments():
	data={}
	
	lid=request.args['lid']
	q="select * from appoinments inner join doctors using(doctor_id) where user_id=(select user_id from users where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)



@api.route('/doctorviewappoinments')
def doctorviewappoinments():
	data={}
	
	lid=request.args['lid']
	q="select * from appoinments inner join users using(user_id) where doctor_id=(select doctor_id from doctors where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)


@api.route('/doctorview_customers')
def doctorview_customers():
	data={}
	
	app_id=request.args['app_id']
	q="select * from users inner join appoinments using(user_id) where appoinment_id='%s'"%(app_id)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)





@api.route('/user_view_doctor_prescription')
def user_view_doctor_prescription():
	data={}
	
	appoi_id=request.args['appoi_id']
	q="select * from prescription inner join appoinments using(appoinment_id) where appoinment_id='%s'"%(appoi_id)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)







@api.route('/doctor_upload_prescription',methods=['get','post'])
def doctor_upload_prescription():
	data={}
	
	app_id=request.form['app_id']
	image=request.files['image']
	path="static/"+str(uuid.uuid4())+image.filename
	image.save(path)
	q="insert into prescription values(null,'%s','%s',curdate(),'pending')"%(app_id,path)
	insert(q)
	data['status']="success"
	return demjson.encode(data)




@api.route('/viewrating')
def viewrating():
	data={}
	lid=request.args['lid']
	bid=request.args['bid']
	q="select * from rating where user_id=(select user_id from users where login_id='%s') and boy_id='%s'" %(lid,bid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res[0]['rated']
	else:
		data['status']="failed"
	data['method']='viewrating'
	return demjson.encode(data)

@api.route('/userrating')
def userrating():
	data={}
	lid=request.args['lid']
	rate=request.args['rate']
	bid=request.args['bid']
	q="select * from rating where user_id=(select user_id from users where login_id='%s') and boy_id='%s'" %(lid,bid)
	res=select(q)
	if res:
		q="update rating set rated='%s' where user_id=(select user_id from users where login_id='%s') and boy_id='%s'" %(rate,lid,bid)
		update(q)
	else:
		q="insert into rating values(null,(select user_id from users where login_id='%s'),'%s','%s',curdate())" %(lid,bid,rate)
		insert(q)
	data['status']="success"
	data['method']='userrating'
	return demjson.encode(data)



@api.route('/userviewdeliveryboys')
def userviewdeliveryboys():
	data={}
	pid=request.args['pid']
	q="SELECT * FROM `delivery` INNER JOIN `deliveryboy` USING(boy_id) WHERE prescription_id='%s'"%(pid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)



@api.route('/deliveryviewrating')
def deliveryviewrating():
	data={}
	lid=request.args['lid']
	q="select * from rating where boy_id=(select boy_id from deliveryboy where login_id='%s')" %(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res[0]['rated']
	else:
		data['status']="failed"
	return demjson.encode(data)


# @api.route('/viewmedicalshop')
# def viewmedicalshop():
# 	data={}
# 	q="select * from medicalshop"
# 	res=select(q)
# 	if res:
# 		data['status']="success"
# 		data['data']=res 
# 	else:
# 		data['status']="failed"
# 		data['method']='viewmedicalshop'
# 	return demjson.encode(data)


@api.route('/user_upload_doctor_prescription_to_medical_shop')
def user_upload_doctor_prescription_to_medical_shop():
	data={}
	img=request.args['file']
	datae=request.args['date']
	statusss=request.args['statusss']
	medicalshop=request.args['medicalshop']
	lid=request.args['lid']
	q="insert into uploadprescription values(null,'%s',(select user_id from users where login_id='%s'),'%s','0',curdate(),'pending')" %(medicalshop,lid,img)
	insert(q)
	data['status']="success"
	data['method']='user_upload_doctor_prescription_to_medical_shop'
	return demjson.encode(data)


@api.route('/viewmedicalshop')
def viewmedicalshop():
	data={}
	q="select * from medicalshop"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res 
	else:
		data['status']="failed"
	data['method']='viewmedicalshop'
	return demjson.encode(data)