from django.db.models.signals import post_save
from django.db.models.signals import pre_save
from django.dispatch import receiver
from core.models import Purchases, Goods

@receiver(post_save, sender=Purchases)
def purchases_post_save(instance, **kwargs):
    good_obj = Goods.objects.get(pk=instance.good.pk)
    good_obj.quantity -= instance.quantity
    good_obj.save()

@receiver(pre_save, sender=Purchases)
def purchases_pre_save(instance, **kwargs):
    good_obj = Goods.objects.get(pk=instance.good.pk)
    if good_obj.quantity < instance.quantity:
        raise Exception('The items with such quantity do not exist')
    instance.money = instance.quantity * instance.good.price