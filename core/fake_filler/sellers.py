from core.models import Seller
from django.contrib.auth.models import User
import urllib.request
import json

def fill(number):
    for i in range(number):
        b = urllib.request.urlopen('https://uinames.com/api/?ext&region=united%20states').read()
        s = b.decode('UTF-8')
        j = json.loads(s)
        username = j['name'].lower() + j['surname'].lower()
        user = User(first_name=j['name'], last_name=j['surname'], email=j['email'], username=username)
        user.save()
        seller = Seller(phone_num=j['phone'])
        seller.user = user
        seller.save()
        print('User ' + username + ' successfully added')