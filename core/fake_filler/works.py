from core.models import Works, Seller, Store

def do():
    sellers = Seller.objects.all()
    stores = Store.objects.all()
    i = 0
    while i < len(sellers):
        for j in range(len(stores)):
            if(i >= len(sellers)):
                break
            w = Works(seller=sellers[i], store=stores[j])
            w.save()
            print(w)
            i = i + 1


