from core.models import Purchases, Goods, Store, Seller
from django.utils import timezone
import random

def fill(number):
    goods_len = Goods.objects.count()
    for i in range(number):
        r = random.randint(0, goods_len-1)
        good = Goods.objects.all()[r]
        sellers = Seller.objects.filter(works__store=good.store)
        seller = sellers[random.randint(0, len(sellers) - 1)]
        q = random.randint(1, good.quantity)
        purchase = Purchases(good=good, seller=seller, quantity=q)
        days = random.randint(1, 1800)
        hours = random.randint(0, 23)
        minutes = random.randint(0, 59)
        delta = timezone.timedelta(days=days, hours=hours, minutes=minutes)
        purchase.date = timezone.now() - delta
        print(purchase)
        purchase.save()