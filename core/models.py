from django.db import models
from django.contrib.auth.models import User
from django.utils import timezone


class Seller(models.Model):
    user = models.OneToOneField(User)
    phone_num = models.CharField(max_length=20, blank=True)

    def __str__(self):
        return '@' + self.user.username + ' ' + self.user.email + ' ' + self.user.first_name + ' ' + \
                self.user.last_name


# class Category(models.Model): maybe add predefined goods' categories?


class Store(models.Model):
    name = models.CharField(max_length=50)
    address = models.CharField(max_length=100)
    phone_num = models.CharField(max_length=20, blank=True)

    def __str__(self):
        return '@' + self.name + ' at ' + self.address


class Works(models.Model):
    seller = models.ForeignKey(Seller, on_delete=models.SET_NULL, null=True)
    store = models.ForeignKey(Store, on_delete=models.SET_NULL, null=True)

    class Meta:
        verbose_name_plural = 'Works'

    def __str__(self):
        return '@' + self.seller.user.username + ' at @' + self.store.name + ' ' + self.store.address


class Goods(models.Model):
    name = models.CharField(max_length=100) # Recently added
    category = models.CharField(max_length=100, default='uncategorized')
    price = models.IntegerField()
    quantity = models.IntegerField()
    bar_code = models.CharField(max_length=255)
    store = models.ForeignKey(Store, on_delete=models.CASCADE)

    class Meta:
        verbose_name_plural = 'Goods'

    def __str__(self):
        return self.name + ' from ' + self.category + ', q: ' + str(self.quantity) + ', at @' + \
               self.store.name + ', price: ' + str(self.price)


class Purchases(models.Model):
    quantity = models.IntegerField()
    date = models.DateTimeField(default=timezone.now)
    seller = models.ForeignKey(Seller, on_delete=models.SET_NULL, null=True)
    good = models.ForeignKey(Goods, on_delete=models.SET_NULL, null=True) # Recently added
    money = models.IntegerField(default=0)

    class Meta:
        verbose_name_plural = 'Purchases'

    def __str__(self):
        return '@' + self.seller.user.username + ' sold ' + self.good.name  + ' at @' \
               + self.good.store.name + ', at ' + self.date.__str__() + ', q: ' +\
               str(self.quantity) + ', m: ' + str(self.money)

    # TODO(aibek): should do pre-check that quantity is not larger than contained in Goods before save()