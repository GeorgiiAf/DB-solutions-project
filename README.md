# DB Solutions Project - Tilausjärjestelmän REST API

Projekti on Spring Boot ja MariaDB -pohjainen REST API tilausjärjestelmälle/verkkokaupalle. 
API tarjoaa kattavat CRUD-toiminnot asiakkaiden, tuotteiden, kategorioiden, toimittajien ja tilausten hallintaan.

## Teknologiat ja Arkkitehtuuri

- **Backend**: Spring Boot 3.5.11
- **Tietokanta**: MariaDB 11.7.2
- **ORM**: Spring Data JPA / Hibernate
- **Java**: 17
- **Build**: Maven

Projekti noudattaa kerrosarkkitehtuuria:

```
Controller
Service
Repository
Entity
DTO
```

- **Controllers** - käsittelevät HTTP-pyynnöt ja palauttavat vastaukset
- **Services** - sisältävät liiketoimintalogiikan ja muunnokset Entity ↔ DTO
- **Repositories** - tietokantaoperaatiot Spring Data JPA:n kautta
- **DTOs** - tiedonsiirto-objektit API:lle
- **Entities** - JPA-entiteetit, jotka vastaavat tietokantatauluja

## Tietokantarakenne

### Taulut

1. **customers** - asiakkaat (tukee perintää yritysasiakkaille)
2. **customeraddresses** - asiakkaiden osoitteet (1:1-suhde)
3. **products** - tuotteet
4. **productcategories** - tuotekategoriat
5. **suppliers** - toimittajat
6. **product_supplier** - monta-moneen-suhde tuotteiden ja toimittajien välillä
7. **orders** - tilaukset
8. **orderitems** - tilausrivit
9. **contacts** - yhteystiedot

### Tietokannan ominaisuudet

#### 1. Indeksit suorituskyvyn optimointiin

- `idx_contacts_email` - sähköpostihaku
- `idx_customers_name` - yhdistetty indeksi etu- ja sukunimelle
- `idx_customers_last_name` - sukunimihaku
- `idx_customers_phone` - puhelinnumerohaku
- `idx_orders_order_date` - tilauspäivämäärähaku
- `idx_orders_status_date` - yhdistetty indeksi tilauksen tilalle ja päivämäärälle

#### 2. Temporaaliset taulut (System Versioning)

**products**-taulu käyttää `SYSTEM VERSIONING` -ominaisuutta:
- Automaattinen muutoshistorian tallennus
- Mahdollisuus hakea historiallisia tietoja
- `row_start` ja `row_end` -kentät aikaleimoja varten
- Mahdollistaa hintojen muutosten seurannan ja tietojen palautuksen

#### 3. Näkymät (Views)

**product_with_category** - näkymä, joka yhdistää tuotteet ja kategoriat helpottaen kyselyitä.

#### 4. Liipaisimet (Triggers)

**before_order_delivered** - liipaisin, joka käsittelee tietoja automaattisesti ennen tilauksen tilan muutosta.

#### 5. Taulujen väliset suhteet

- **One-to-One**: Customer ↔ CustomerAddress
- **One-to-Many**: 
  - ProductCategory → Products
  - Customer → Orders
  - Order → OrderItems
- **Many-to-Many**: Products ↔ Suppliers (product_supplier-taulun kautta)

#### 6. Periytyminen (Inheritance)

`customers`-taulu käyttää `SINGLE_TABLE` -strategiaa:
- Tavalliset asiakkaat (PERSON)
- Yritysasiakkaat (COMPANY) CompanyCustomer-luokan kautta
- Erotteleva kenttä: `customer_type`

## API-päätepisteet

### 1. Asiakkaat (Customers)

**Perus-URL**: `/customers`

#### GET /customers
Hakee kaikki asiakkaat.

**Käyttötarkoitus**: Asiakaslistauksen näyttäminen verkkokaupassa tai hallintapaneelissa.

**Vastaus**:
```json
[
  {
    "id": 1,
    "firstName": "Matti",
    "lastName": "Meikäläinen",
    "email": "matti@example.com",
    "customersPhone": "+358401234567"
  }
]
```

#### GET /customers/{id}
Hakee yksittäisen asiakkaan ID:n perusteella.

**Käyttötarkoitus**: Asiakastietojen näyttäminen ja muokkaus.

**Vastaus**: Yksittäinen asiakasobjekti (sama rakenne kuin yllä).

#### POST /customers
Luo uuden asiakkaan.

**Käyttötarkoitus**: Rekisteröityminen verkkokauppaan.

**Pyynnön body**:
```json
{
  "firstName": "Matti",
  "lastName": "Meikäläinen",
  "email": "matti@example.com",
  "customersPhone": "+358401234567"
}
```

**Vastaus**: Luotu asiakasobjekti ID:llä.

#### POST /customers/company
Luo yritysasiakkaan (käyttää periytymistä).

**Käyttötarkoitus**: B2B-asiakkaiden rekisteröinti.

**Pyynnön body**:
```json
{
  "firstName": "Yritys",
  "lastName": "Oy",
  "email": "yritys@example.com",
  "customersPhone": "+358401234567",
  "companyName": "Tech Oy",
  "businessId": "1234567-8"
}
```

#### PUT /customers/{id}
Päivittää asiakkaan tiedot.

**Käyttötarkoitus**: Profiilitietojen muokkaus.

#### DELETE /customers/{id}
Poistaa asiakkaan.

---

### 2. Tuotteet (Products)

**Perus-URL**: `/products`

#### GET /products
Hakee kaikki tuotteet.

**Käyttötarkoitus**: Tuotelistauksen näyttäminen verkkokaupassa.

**Vastaus**:
```json
[
  {
    "id": 1,
    "name": "Kannettava tietokone",
    "description": "Tehokas kannettava",
    "price": 999.99,
    "stockQuantity": 50,
    "categoryId": 1
  }
]
```

#### GET /products/{id}
Hakee yksittäisen tuotteen.

**Käyttötarkoitus**: Tuotesivun näyttäminen.

#### POST /products
Luo uuden tuotteen.

**Käyttötarkoitus**: Tuotteiden lisääminen järjestelmään (admin).

**Pyynnön body**:
```json
{
  "name": "Kannettava tietokone",
  "description": "Tehokas kannettava",
  "price": 999.99,
  "stockQuantity": 50,
  "categoryId": 1
}
```

#### PUT /products/{id}
Päivittää tuotteen tiedot.

**Käyttötarkoitus**: Hintojen ja varastosaldon päivitys.

**Huom**: Muutokset tallentuvat automaattisesti historiaan (System Versioning).

#### DELETE /products/{id}
Poistaa tuotteen.

---

### 3. Tuotekategoriat (Product Categories)

**Perus-URL**: `/categories`

#### GET /categories
Hakee kaikki kategoriat.

**Käyttötarkoitus**: Kategoriavalikon näyttäminen.

**Vastaus**:
```json
[
  {
    "id": 1,
    "name": "Elektroniikka",
    "description": "Elektroniset laitteet ja tarvikkeet"
  }
]
```

#### GET /categories/{id}
Hakee yksittäisen kategorian.

#### POST /categories
Luo uuden kategorian.

**Pyynnön body**:
```json
{
  "name": "Elektroniikka",
  "description": "Elektroniset laitteet ja tarvikkeet"
}
```

#### PUT /categories/{id}
Päivittää kategorian.

#### DELETE /categories/{id}
Poistaa kategorian.

---

### 4. Toimittajat (Suppliers)

**Perus-URL**: `/suppliers`

#### GET /suppliers
Hakee kaikki toimittajat.

**Käyttötarkoitus**: Toimittajahallinta.

**Vastaus**:
```json
[
  {
    "id": 1,
    "name": "Tech Supplier Oy",
    "email": "toimittaja@example.com",
    "phone": "+358501234567"
  }
]
```

#### GET /suppliers/{id}
Hakee yksittäisen toimittajan.

#### POST /suppliers
Luo uuden toimittajan.

**Pyynnön body**:
```json
{
  "name": "Tech Supplier Oy",
  "email": "toimittaja@example.com",
  "phone": "+358501234567"
}
```

#### PUT /suppliers/{id}
Päivittää toimittajan tiedot.

#### DELETE /suppliers/{id}
Poistaa toimittajan.

---

### 5. Tilaukset (Orders)

**Perus-URL**: `/orders`

#### GET /orders
Hakee kaikki tilaukset.

**Käyttötarkoitus**: Tilaushistorian ja hallintapaneelin näyttäminen.

**Vastaus**:
```json
[
  {
    "id": 1,
    "orderDate": "2024-03-10T10:30:00",
    "customerId": 1,
    "items": [
      {
        "id": 1,
        "productId": 1,
        "quantity": 2,
        "price": 999.99
      }
    ]
  }
]
```

#### GET /orders/{id}
Hakee yksittäisen tilauksen.

**Käyttötarkoitus**: Tilauksen yksityiskohtien näyttäminen.

#### POST /orders
Luo uuden tilauksen.

**Käyttötarkoitus**: Ostoskorin tilaaminen.

**request body**:
```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**Huom**: Tuotteen hinta haetaan automaattisesti products-taulusta tilauksen luontihetkellä. Tämä varmistaa, että tilauksen hinta säilyy oikeana, vaikka tuotteen hinta muuttuisi myöhemmin.

#### DELETE /orders/{id}
Poistaa tilauksen.

---

## Toteutetut ominaisuudet ja toiminnallisuudet

### 1. DTO-malli (Data Transfer Objects)
Kaikki API-päätepisteet käyttävät DTO-objekteja Entity-objektien sijaan:
- Piilottaa tietokannan sisäisen rakenteen
- Estää lazy loading -ongelmat
- Mahdollistaa API-datan muodon hallinnan
- Parantaa suorituskykyä pienentämällä siirrettävän datan määrää

### 2. Palvelukerros (Service Layer)
Kaikki liiketoimintalogiikka on eriytetty palvelukerrokseen:
- Entity ↔ DTO -muunnokset
- Tietojen validointi
- Transaktioiden hallinta
- Selkeä vastuunjako kerrosten välillä

### 3. Suhteiden käsittely
- **Tilaukset**: Tilauksen luonnissa ladataan automaattisesti asiakkaan ja tuotteiden tiedot
- **Tuotteet**: Tuki kategorioille ja toimittajille (monta-moneen-suhde)
- **Asiakkaat**: Periytymisen tuki eri asiakastyypeille (henkilö/yritys)

### 4. Automaattinen hinnoittelu
Tilauksen luonnissa tuotteen hinta haetaan automaattisesti products-taulusta. Tämä varmistaa:
- Hinnan säilymisen oikeana historiallisesti
- Tilauksen hinnan pysyvyyden, vaikka tuotteen hinta muuttuu
- Tietojen eheyden

### 5. Tietokannan optimointi ja turvallisuus

#### Indeksointi
Kriittiset kentät on indeksoitu nopeaa hakua varten:
- Asiakkaiden sähköpostit (idx_contacts_email)
- Asiakkaiden nimet (idx_customers_name, idx_customers_last_name)
- Puhelinnumerot (idx_customers_phone)
- Tilauspäivämäärät (idx_orders_order_date)
- Tilausten tilat ja päivämäärät (idx_orders_status_date)

Yhdistetyt indeksit (composite indexes) optimoivat usein käytettyjä hakukombinaatioita.

#### Temporaaliset taulut
Products-taulun System Versioning mahdollistaa:
- Automaattisen muutoshistorian tallennuksen
- Hintojen muutosten seurannan
- Tietojen palauttamisen aikaisempaan tilaan
- Auditoinnin ja raportoinnin

#### Näkymät (Views)
product_with_category-näkymä:
- Yksinkertaistaa tuotteiden ja kategorioiden yhdistämistä
- Parantaa kyselyiden luettavuutta
- Vähentää toistuvan koodin määrää

#### Liipaisimet (Triggers)
before_order_delivered-liipaisin:
- Automaattinen tietojen käsittely tilauksen tilan muuttuessa
- Varmistaa tietojen eheyden
- Mahdollistaa liiketoimintalogiikan toteuttamisen tietokantatasolla

### 6. Lazy Loading
Liittyvät entiteetit ladataan vain tarvittaessa:
- Parantaa suorituskykyä
- Vähentää muistin käyttöä
- Estää tarpeettoman datan lataamisen
