from django.conf.urls import url
from . import views as core_views
from django.views.generic import RedirectView

app_name = 'core'
urlpatterns = [
    url(r'^home/', core_views.home, name='home'),
    url(r'^store/(?P<store_pk>[0-9]+)/$', core_views.store, name='store'),
    url(r'^seller/(?P<seller_pk>[0-9]+)/$', core_views.seller, name='seller'),
    url(r'^rev_made_over_period/$', core_views.rev_made_over_period, name='rev_made_over_period'),
    url(r'^items_sold_by_seller/$', core_views.items_sold_by_seller, name='items_sold_by_seller'),
    url(r'^goods/(?P<store_pk>[0-9]+)/$', core_views.goods, name='goods'),
    url(r'^goods/$', core_views.goods, name='goods'),
    url(r'^purchases/(?P<store_pk>[0-9]+)/$', core_views.purchases, name='purchases'),
    url(r'^purchases/$', core_views.purchases, name='purchases'),
    url(r'^most_popular_goods/$', core_views.most_popular_goods, name='most_popular_goods'),
    url(r'^$', RedirectView.as_view(pattern_name='login')),
]