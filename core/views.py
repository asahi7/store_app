from django.shortcuts import render, get_object_or_404, get_list_or_404
from django.http import Http404
from django.contrib.auth.decorators import login_required
from django.db.models import Count
from django.utils import timezone
from django.db import models
from django.db.models import Func, F, Sum
from django.db.models.functions import TruncMonth, TruncYear, TruncDay
from .models import Store, Seller, Purchases, Works, Goods
import datetime, json

@login_required
def home(request):
    stores = Store.objects.all()
    sellers = Seller.objects.all()
    purchases = Purchases.objects.order_by('-date')[:10] # maybe it goes in asc order?
    context = {
        'stores': stores,
        'sellers': sellers,
        'purchases': purchases,
    }
    return render(request, 'core/home.html', context)

@login_required
def store(request, store_pk=None):
    if store_pk is None:
        raise Http404('Store is not specified')
    store_obj = get_object_or_404(Store, pk=store_pk)
    purchases = Purchases.objects.filter(good__store=store_obj).order_by('-date')[:10]
    return render(request, 'core/store.html', {'store': store_obj, 'purchases': purchases})

@login_required
def seller(request, seller_pk=None):
    if seller_pk is None:
        raise Http404('Seller is not specified')
    seller_obj = get_object_or_404(Seller, pk=seller_pk)
    purchases = Purchases.objects.filter(seller=seller_obj).order_by('-date')
    return render(request, 'core/seller.html', {'seller': seller_obj, 'purchases': purchases})

@login_required
def items_sold_by_seller(request):
    begin = request.GET.get('begin')
    end = request.GET.get('end')
    show = request.GET.get('show')
    if show is None or show == '':
        show = 12
    else:
        show = int(show)
    if begin is None or begin == '':
        begin = datetime.datetime.min
    else:
        begin = datetime.datetime.strptime(begin, '%Y-%m-%d')
    if end is None or end == '':
        end = datetime.datetime.now()
    else:
        end = datetime.datetime.strptime(end, '%Y-%m-%d')
    begin = timezone.make_aware(begin, timezone.get_current_timezone())
    end = timezone.make_aware(end, timezone.get_current_timezone())
    sellers = Seller.objects.filter(purchases__date__gt=begin, purchases__date__lt=end).\
        annotate(purchases_count=Count('purchases')).order_by('-purchases_count')[:show]
    json_data = []
    for seller in sellers:
        new_obj = {}
        new_obj['seller_pk'] = seller.user.pk
        new_obj['seller_username'] = seller.user.username
        new_obj['count'] = seller.purchases_count
        json_data.append(new_obj)
    json_data = json.dumps(json_data)
    return render(request, 'vis/items_sold_by_seller.html', {'sellers': sellers, 'json_data': json_data})

@login_required
def rev_made_over_period(request):
    begin = request.GET.get('begin')
    end = request.GET.get('end')
    group_by = request.GET.get('group_by')
    show = request.GET.get('show')
    if show is None or show == '':
        show = 12
    else:
        show = int(show)
    if begin is None or begin == '':
        begin = datetime.datetime.min
    else:
        begin = datetime.datetime.strptime(begin, '%Y-%m-%d')
    if end is None or end == '':
        end = datetime.datetime.now()
    else:
        end = datetime.datetime.strptime(end, '%Y-%m-%d')
    if group_by is None or group_by == '':
        group_by = 'month'
    if group_by == 'year':
        trunc = TruncYear
    elif group_by == 'month':
        trunc = TruncMonth
    elif group_by == 'day':
        trunc = TruncDay
    else:
        raise Http404('Not Found')
    begin = timezone.make_aware(begin, timezone.get_current_timezone())
    end = timezone.make_aware(end, timezone.get_current_timezone())
    purchases = Purchases.objects.filter(date__gt=begin, date__lt=end)
    revenue = (purchases
                .annotate(period=trunc('date'))
                .values('period')
                .annotate(total=Sum('money'))
                .order_by('-period'))[:show]
    json_data = []
    for revenue_obj in revenue:
        new_obj = {}
        new_obj['period'] = revenue_obj['period'].isoformat()
        new_obj['total'] = revenue_obj['total']
        json_data.append(new_obj)
    json_data.reverse()
    json_data = json.dumps(json_data)
    return render(request, 'vis/rev_made_over_period.html', {'revenue': revenue, 'json_data': json_data})

@login_required()
def most_popular_goods(request):
    type = request.GET.get('type')
    if type is None or type == '':
        as_text = True
        as_chart = False
    elif type == 'chart':
        as_chart = True
        as_text = False
    else:
        as_chart = False
        as_text = True
    show = request.GET.get('show')
    if show is None or show == '':
        show = 12
    else:
        show = int(show)
    goods = Goods.objects.all()
    goods = goods.annotate(purchases_count=Count('purchases')).order_by('-purchases_count')[:show]
    json_data = []
    for good in goods:
        new_obj = {}
        new_obj['name'] = good.name
        new_obj['pk'] = good.pk
        new_obj['purchases_count'] = good.purchases_count
        json_data.append(new_obj)
    json_data = json.dumps(json_data)
    return render(request, 'vis/most_popular_goods.html', {'json_data': json_data, 'goods': goods, 'as_chart': as_chart, 'as_text': as_text})


@login_required
def goods(request, store_pk=None):
    store_obj = None
    if store_pk is None:
        goods = Goods.objects.all()
    else:
        goods = Goods.objects.filter(store__pk=store_pk)
        store_obj = get_object_or_404(Store, pk=store_pk)
    return render(request, 'core/goods.html', {'goods': goods, 'store': store_obj})

@login_required
def purchases(request, store_pk=None):
    store_obj = None
    if store_pk is None:
        purchases_obj = Purchases.objects.all()
    else:
        purchases_obj = Purchases.objects.filter(good__store__pk=store_pk)
        store_obj = get_object_or_404(Store, pk=store_pk)
    purchases_obj = purchases_obj.order_by('-date')
    return render(request, 'core/purchases.html', {'purchases': purchases_obj, 'store': store_obj})

