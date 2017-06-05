from core.models import Store
import random

data = '''Berry Hill
Lake Davis
Ansonville
Jeffers
Crowley
Homecroft
Westover
Sterling
Grand Ledge
Topawa
Pomona
La Veta
Palm Shores
Quakertown
Evening Shade
Selden
Throop
Lakewood
Stonewall
Robeline
Annetta
Devola
Broadview
La Feria
Fleming
Pinch
Caddo Valley
Alex
Port Sanilac
Washington Terrace
North River Shores
Elizabethton
Briarcliff
Clarkson
Culpeper
Malta
North Grosvenor
Hooverson Heights
Blackshear
Fort Washakie
Christopher Creek
Loveland
Port LaBelle
Black Canyon
Beersheba Springs
Kingston
Perla
Maize
Spring Lake
Rolling Fields
Shedd
Bellerive
Sunizona
West Portsmouth
Union Grove
Edina
Darrington
Southbridge
Stewart
Wausa
Wyocena
Honeyville
Blooming Prairie
Andes
Destrehan
Eden Prairie
Colmar Manor
Midway City
Thawville
Toughkenamon
Bonneau
Brooklyn
Kennedy
Sikeston'''

def fill(number):
    spl = data.split('\n')
    used = {}
    for i in range(number):
        length = len(spl)
        pos = random.randint(0, length - 1)
        while (pos in used.keys() and used[pos]):
            pos = random.randint(0, length - 1)
        used[pos] = True
        name = spl[pos]
        address = 'fakeCity, fakeStr ' + str(i)
        store = Store(name=name, address=address)
        store.save()
        print(store.__str__() + ', store was added')