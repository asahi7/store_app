from django.contrib import admin
from .models import Seller, Purchases, Goods, Store, Works

admin.site.register([Seller, Purchases, Goods, Store, Works])