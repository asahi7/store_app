from core.models import Goods, Store
import random

def fill(number):
    last = Goods.objects.count() + 1
    store_len = Store.objects.count()
    for i in range(number):
        r = random.randint(0, store_len - 1)
        p = random.randint(500, 10000)
        q = random.randint(1, 10)
        b = 'fakebarcode' + str(last + i)
        n = 'Good' + str(last + i)
        store = Store.objects.all()[r]
        good = Goods(store=store, price=p, quantity=q, bar_code=b, name=n)
        print(good)
        good.save()