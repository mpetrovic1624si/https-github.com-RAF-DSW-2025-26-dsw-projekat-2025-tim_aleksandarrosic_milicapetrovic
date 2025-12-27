# GRAFfito - Detaljno Objašnjenje Projekta za Usmenu Odbranu

## 1. UVOD I OPIS APLIKACIJE

**GRAFfito** je desktop aplikacija za kreiranje i uređivanje prezentacija, implementirana u Java programskom jeziku koristeći Swing GUI framework. Aplikacija omogućava korisnicima da kreiraju projekte, prezentacije i slajdove, na koje mogu dodavati različite vizuelne elemente (slike, tekst, logo), manipulirati njima i čuvati ih u JSON formatu.

### 1.1 Osnovne Karakteristike
- **MVC arhitektura** - jasno razdvojen model, view i controller
- **Dizajn obrasci** - Observer, Command, Factory, Strategy
- **JSON serijalizacija** - čuvanje i učitavanje projekata
- **Undo/Redo mehanizam** - podrška za vraćanje akcija
- **Modularna struktura** - lako proširivanje funkcionalnosti

---

## 2. ARHITEKTURA I STRUKTURA PROJEKTA

### 2.1 Paketi i Organizacija Koda

```
raf.graffito.dsw/
├── core/                    # Core funkcionalnost
│   ├── graff/              # Model strukture (Workspace, Project, Presentation, Slide)
│   ├── logger/             # Logging sistem
│   └── message/            # Message generator i subscriber
├── controller/              # Kontroleri i akcije
│   ├── toolmodes/          # Različiti načini rada (Strategy pattern)
│   ├── undo/               # Command pattern za Undo/Redo
│   ├── serializer/         # JSON serijalizacija
│   └── spacechecker/       # Provera dostupnog prostora
├── gui/                    # Korisnički interfejs
│   └── swing/              # Swing komponente
└── AppCore.java            # Glavni ulazni tačka

app/
├── model/                  # Model slajd elemenata
├── view/                   # View komponente
└── observer/               # Observer pattern implementacija
```

### 2.2 MVC Arhitektura

**MODEL:**
- `GraffRepository` - glavni repository koji upravlja Workspace-om
- `Slide` (model.Slide) - sadrži sve elemente slajda
- `SlideElement` - apstraktna klasa za sve elemente (ImageElement, TextElement, LogoElement)

**VIEW:**
- `MainFrame` - glavni prozor aplikacije
- `SlideView` - komponenta za prikaz i iscrtavanje slajda
- `GraffTreeImplementation` - JTree komponenta za hijerarhiju projekata
- `RightToolBar` - desni toolbar sa alatkama

**CONTROLLER:**
- `SlideController` - upravlja interakcijama sa slajdom
- `TreeController` - upravlja interakcijama sa stablom
- `ActionManager` - upravlja svim akcijama aplikacije
- Tool mode kontroleri (AddMode, SelectMode, MoveMode, itd.)

---

## 3. DIZAJN OBRASCI (DESIGN PATTERNS)

### 3.1 Observer Pattern
**Gde se koristi:**
- `SlideObserver` - Slide obaveštava SlideView o promenama
- `GraffRepository` obaveštava JTree o promenama strukture
- `Presentation` obaveštava tabbed pane o promenama slajdova

**Kako funkcioniše:**
```java
// Slide ima listu observera
slide.addObserver(slideView);
// Kada se promeni slide:
slide.notifyObservers(); // Obaveštava sve registrovane observere
```

**Zašto je važno:**
- Omogućava automatsko ažuriranje UI-ja kada se promeni model
- Ne postoji direktna zavisnost između modela i view-a
- Lako dodavanje novih observera

### 3.2 Command Pattern (Undo/Redo)
**Gde se koristi:**
- `UndoManager` - upravlja stack-om komandi
- Svi tipovi komandi: `AddElementCommand`, `DeleteElementCommand`, `MoveElementCommand`, `RotateElementCommand`, itd.

**Kako funkcioniše:**
```java
// Svaka akcija je Command objekat
Command cmd = new AddElementCommand(slide, element);
undoManager.executeCommand(cmd); // Izvršava i čuva u stack

// Undo vraća prethodno stanje
undoManager.undo(); // Vraća poslednju komandu
undoManager.redo(); // Ponovo izvršava vraćenu komandu
```

**Zašto je važno:**
- Omogućava jednostavnu implementaciju Undo/Redo funkcionalnosti
- Svaka komanda može da se ponovi, poništi, ili čuva
- Jedinstven interfejs za sve akcije

### 3.3 Strategy Pattern (Tool Modes)
**Gde se koristi:**
- `ToolMode` interfejs - svi načini rada implementiraju ovaj interfejs
- Konkretne strategije: `AddMode`, `SelectMode`, `MoveMode`, `ResizeMode`, `RotateMode`, `ZoomMode`

**Kako funkcioniše:**
```java
// SlideController menja strategiju na osnovu korisnikovog izbora
slideController.setMode(addMode); // Postavlja AddMode
slideController.setMode(selectMode); // Menja na SelectMode

// Sve strategije imaju iste metode
toolMode.mousePressed(e);
toolMode.mouseDragged(e);
toolMode.mouseReleased(e);
```

**Zašto je važno:**
- Lako dodavanje novih načina rada
- Jasno razdvojeni različiti načini manipulacije
- Dinamičko menjanje ponašanja aplikacije

### 3.4 Factory Pattern
**Gde se koristi:**
- `FactoryGenerator` - generiše factory objekte za različite tipove čvorova
- `ProjectFactory`, `PresentationFactory`, `SlideFactory`

**Kako funkcioniše:**
```java
// Factory generator vraća odgovarajuću factory na osnovu tipa čvora
GraffNodeFactory factory = FactoryGenerator.getFactory(parentNode);
GraffNode newNode = factory.createNode(parentNode);
```

**Zašto je važno:**
- Centralizovano kreiranje objekata
- Lako proširivanje za nove tipove čvorova
- Enkapsulacija logike kreiranja

### 3.5 Singleton Pattern
**Gde se koristi:**
- `MainFrame.getInstance()` - samo jedna instanca glavnog prozora
- `SlideControllerManager.getInstance()` - centralizovano upravljanje trenutnim controllerom
- `SpaceCheckerManager.getInstance()` - upravljanje proverom prostora

**Zašto je važno:**
- Osigurava samo jednu instancu kritičnih objekata
- Globalna tačka pristupa
- Kontrola pristupa resursima

---

## 4. KLIJUČNE FUNKCIONALNOSTI

### 4.1 Hijerarhijska Struktura Projekata

**Struktura:**
```
Workspace
  └── Project
      └── Presentation
          └── Slide
              └── Element (Image, Text, Logo)
```

**Implementacija:**
- `Workspace`, `Project`, `Presentation` - kompozitni čvorovi (`GraffNodeComposite`)
- `Slide` - leaf čvor (`GraffLeaf`)
- `GraffRepository` - validira i kontroliše hijerarhiju

**Pravila hijerarhije:**
- Workspace može imati samo Project čvorove
- Project može imati Presentation ili Slide čvorove
- Presentation može imati samo Slide čvorove
- Slide je leaf i ne može imati decu

### 4.2 Rad sa Slajdovima

**SlideView komponenta:**
- Iscrtava sve elemente slajda sa transformacijama (rotacija, zoom)
- Koristi Graphics2D za 2D transformacije
- Observer pattern za automatsko ažuriranje

**Tipovi elemenata:**
1. **ImageElement** - učitana slika sa diska
2. **TextElement** - tekstualni sadržaj sa fontom i veličinom
3. **LogoElement** - apstraktni grafički oblik (trougao)

**Sve elemente karakteriše:**
- Pozicija (x, y)
- Dimenzije (width, height)
- Rotacija (u stepenima)
- Selektovanje (selected flag)

### 4.3 Načini Rada (Tool Modes)

**1. AddMode - Dodavanje Elemenata**
- Klik na slajd dodaje element na tu poziciju
- Ctrl+Click otvara dijalog za izbor tipa (Image/Text/Logo)
- Koristi izabranu sliku iz ImageLoaderPanel ako je selektovana
- Provera dostupnog prostora pre dodavanja

**2. SelectMode - Selekcija Elemenata**
- Klik na element selektuje samo taj element
- Drag mišom kreira pravougaonik za multi-selekciju
- Selektovani elementi imaju plavi dashed border

**3. MoveMode - Pomeranje Elemenata**
- Drag selektovanih elemenata ih pomera
- Može pomerati više elemenata istovremeno
- Undo/Redo podrška

**4. ResizeMode - Promena Dimenzija**
- Drag selektovanog elementa menja njegove dimenzije
- Minimum dimenzije: 10x10 piksela
- Može resize-ovati više elemenata

**5. RotateMode - Rotacija Elemenata**
- Dugmad za 90° levo/desno
- Rotacija koristi matrice transformacije
- Može rotirati više elemenata istovremeno

**6. ZoomMode - Zumiranje Slajda**
- Mouse wheel zoom in/out
- Dugmad za zoom (+ i -)
- Transformacije primenjene na ceo slajd

### 4.4 Undo/Redo Mehanizam

**Implementacija:**
- `UndoManager` čuva stack komandi
- Svaka komanda ima `execute()` i `undo()` metode
- Execute dodaje komandu u undo stack i briše redo stack

**Podržane operacije:**
- ✅ Dodavanje elemenata
- ✅ Brisanje elemenata
- ✅ Pomeranje elemenata (i multi-selekcion)
- ✅ Promena dimenzija (resize)
- ✅ Rotacija elemenata
- ✅ Copy/Paste
- ❌ Zoom i selektovanje NEMAJU undo (po zahtevu)

### 4.5 Čuvanje i Učitavanje (JSON Serijalizacija)

**Serializer klasa:**
- Koristi Jackson biblioteku za JSON
- Serijalizuje samo model (ne view komponente)
- Čuva hijerarhiju Workspace > Project > Presentation > Slide

**Akcije:**
- **Save** - čuva na trenutnu putanju (ako postoji)
- **Save As** - korisnik bira novu lokaciju
- **Open** - učitava projekat iz JSON fajla
- **Save Template** - čuva u resources folder kao šablon
- **Load Template** - učitava šablon u trenutni projekat

**Kako funkcioniše:**
```java
// Čuvanje
ObjectMapper mapper = new ObjectMapper();
mapper.writeValue(file, workspace);

// Učitavanje
Workspace workspace = mapper.readValue(file, Workspace.class);
// Rekonstrukcija parent relacija
```

### 4.6 Provera Dostupnog Prostora

**Zahtev:** Pre dodavanja novog elementa, proveriti da li je dostupno najmanje 20% slobodnog prostora na slajdu.

**Dva načina implementacije:**

**1. SumAreaSpaceChecker:**
- Sabira površine svih postojećih elemenata
- Ne uzima u obzir preklapanja
- Brže izvršavanje

**2. PixelMatrixSpaceChecker:**
- Slajd kao binarna matrica (0=slobodan, 1=zauzet)
- Rešava problem preklapanja iz 1. načina
- Sporije ali preciznije

**Kako se koristi:**
```java
SpaceCheckerManager manager = SpaceCheckerManager.getInstance();
if (manager.hasEnoughSpace(slide, newElement)) {
    // Dodaj element
} else {
    // Prikaži upozorenje
}
```

**Strategy pattern za dinamičko menjanje načina provere**

### 4.7 Promena Režima Glavnog Prozora

**Tri režima:**
1. **Normal** - standardna veličina (800x600 ili definisana)
2. **Fullscreen** - ceo ekran
3. **Small** - 2x manja od normalnog (400x300)

**Implementacija:**
- `WindowModeController` - upravlja promenom režima
- Radio buttons u toolbar-u za izbor režima
- Automatsko skaliranje slajda u zavisnosti od režima
- Svi elementi skaliraju se proporcionalno

**Kako funkcioniše:**
```java
// Promena režima
WindowModeController.getInstance().setMode(WindowMode.FULLSCREEN);

// Dobijanje faktora skaliranja
double scale = controller.getScaleFactor();
```

---

## 5. KORISNIČKI INTERFEJS

### 5.1 Komponente UI-a

**Menu Bar (MyMenuBar):**
- **File** (dropdown menu):
  - Save - čuvanje projekta (Ctrl+S)
  - Save As - čuvanje na novoj lokaciji (Ctrl+Shift+S)
  - Open - otvaranje projekta (Ctrl+O)
  - Save Template - čuvanje kao šablon
  - Load Template - učitavanje šablona
  - Exit - izlaz iz aplikacije
- **AboutUs** - informacije o aplikaciji
- **DeleteNode** - brisanje čvora iz stabla
- **AddNode** - dodavanje čvora u stablo

**Gornji Toolbar (MyToolBar):**
- Dugmad za dodavanje/brisanje čvorova
- Exit dugme

**Leva Strana:**
- **JTree** - hijerarhija projekata (gore)
- **ImageLoaderPanel** - lista učitanih slika (dole)
  - Dugme "Učitaj Sliku" otvara JFileChooser
  - ScrollPane sa thumbnail slikama
  - Klik na thumbnail selektuje sliku za dodavanje

**Centralni Deo:**
- **JTabbedPane** - tabovi za svaki otvoreni slajd
- Svaki tab prikazuje SlideView komponentu

**Desna Strana (RightToolBar):**
- **Tool Mode dugmad:**
  - Add - dodavanje elemenata
  - Select - selekcija elemenata
  - Move - pomeranje elemenata
  - Resize - promena dimenzija
  - ∞ (Rotate) - rotacija mode
  - ↺ (Rotate 90° Left)
  - ↻ (Rotate 90° Right)
- **Zoom kontrole:**
  - - (Zoom Out)
  - + (Zoom In) sa "select" labelom
  - 🔍 (Reset Zoom)

**Window Mode Panel:**
- Radio buttons: Normal / Fullscreen / Small

### 5.2 Interakcije i Shortcut-i

**Mouse interakcije:**
- Klik - zavisi od aktivnog tool mode-a
- Drag - selekcija ili manipulacija
- Mouse Wheel - zoom in/out
- Double Click na čvor u stablu - otvara tabove

**Keyboard Shortcuts:**
- **Delete / Backspace** - briše selektovane elemente
- **Ctrl+C** - kopira selektovane elemente
- **Ctrl+V** - lepi elemente
- **Ctrl+Click u AddMode** - otvara dijalog za izbor tipa elementa

---

## 6. TEHNIČKI DETALJI

### 6.1 Transformacije i Matrice

**Rotacija elemenata:**
- Koristi `AffineTransform` sa rotacionom matricom
- Rotacija oko centra elementa
- `element.getRotation()` čuva ugao u stepenima

**Zoom transformacije:**
- Primena `AffineTransform.scale()` na Graphics2D kontekst
- Zoom nivo: 0.25x do 4.0x
- Sve elemente jednako skalira

**Implementacija:**
```java
// Rotacija
AffineTransform rotation = new AffineTransform();
rotation.rotate(Math.toRadians(angle), centerX, centerY);
g2d.transform(rotation);

// Zoom
AffineTransform zoom = new AffineTransform();
zoom.scale(zoomLevel, zoomLevel);
g2d.transform(zoom);
```

### 6.2 Observer Pattern Implementacija

**Slide Observer:**
```java
// Model
public interface SlideObserver {
    void slideChanged();
}

// View
public class SlideView implements SlideObserver {
    @Override
    public void slideChanged() {
        repaint(); // Ažurira prikaz
    }
}

// Model obaveštava
slide.notifyObservers();
```

**Repository Observer:**
- GraffRepository obaveštava JTree kada se promeni struktura
- Automatsko osvežavanje stabla

### 6.3 JSON Serijalizacija Detalji

**Šta se čuva:**
- ✅ Workspace struktura (Project, Presentation, Slide)
- ✅ Ime čvorova
- ✅ Elementi slajda (ImageElement, TextElement, LogoElement)
- ✅ Pozicije, dimenzije, rotacija elemenata
- ✅ Putanje do slika
- ❌ View komponente se NE čuvaju
- ❌ Trenutno selektovani elementi se NE čuvaju

**Rekonstrukcija:**
- Prilikom učitavanja, rekonstruišu se parent-child relacije
- Kreiraju se novi SlideView objekti za svaki slajd
- SlideController se kreira za svaki otvoreni tab

### 6.4 Memory Management

**SlideControllerManager:**
- Čuva mapu SlideController objekata po imenu slajda
- Samo aktivni controller je dostupan preko `getCurrentController()`
- Kontroleri se kreiraju kada se otvori tab
- Ne brišu se automatski kada se zatvori tab (može se optimizovati)

---

## 7. KLJUČNE KLASE I NJIHOVE ULОGE

### 7.1 Core Klase

**`AppCore.java`**
- Glavni ulazni tačka aplikacije
- Inicijalizuje ApplicationFramework i MainFrame

**`ApplicationFramework.java`**
- Centralizovano upravljanje aplikacijom
- Povezuje sve komponente

**`GraffRepository.java`**
- Upravlja Workspace objektom
- Validira hijerarhiju čvorova
- Observer pattern za obaveštavanje o promenama
- Markiranje promenjenih projekata

### 7.2 Controller Klase

**`SlideController.java`**
- Upravlja jednim slajdom
- Kreira i menja tool mode-ove
- Obavlja Undo/Redo operacije
- Povezuje mouse/keyboard evente sa tool mode-ovima

**`TreeController.java`**
- Upravlja interakcijama sa JTree
- Double click otvara tabove
- Kreira SlideView i SlideController za svaki slajd
- Povezuje sa SlideControllerManager

**`SlideControllerManager.java` (Singleton)**
- Centralizovano upravljanje trenutno aktivnim SlideController-om
- Prati promene tabbed pane-a
- Omogućava toolbar akcijama pristup trenutnom controlleru

### 7.3 Tool Mode Klase

**`AddMode.java`**
- Dodaje nove elemente na klik
- Koristi ElementTypeSelector za izbor tipa
- Proverava dostupnost prostora pre dodavanja

**`SelectMode.java`**
- Selekcija pojedinačnih ili više elemenata
- Drag pravougaonik za multi-selekciju

**`MoveMode.java`**
- Pomeranje selektovanih elemenata
- Praćenje početnih i krajnjih pozicija za undo

**`ResizeMode.java`**
- Promena dimenzija selektovanih elemenata
- Minimum dimenzije

**`RotateMode.java`**
- Rotacija elemenata
- Metoda `rotateSelected(double degrees)`

**`ZoomMode.java`**
- Zoom funkcionalnost
- Povezan sa SlideView

### 7.4 View Klase

**`MainFrame.java`**
- Glavni prozor aplikacije
- Layout: BorderLayout
  - North: Toolbar + Window Mode Panel
  - Center: SplitPane (Tree/Images | TabbedPane)
  - East: RightToolBar
- Singleton pattern

**`SlideView.java`**
- JPanel koji iscrtava slajd
- Iscrtavanje elemenata sa transformacijama
- Zoom funkcionalnost
- Observer pattern (SlideObserver)

**`RightToolBar.java`**
- Vertikalni toolbar sa alatkama
- ButtonGroup za tool mode dugmad
- Zoom kontrole

### 7.5 Manager i Utility Klase

**`ImageLoaderManager.java` (Singleton)**
- Čuva trenutno selektovanu sliku
- Povezan sa ImageLoaderPanel i AddMode

**`SpaceCheckerManager.java` (Singleton)**
- Upravlja proverom dostupnog prostora
- Strategy pattern - može se menjati način provere
- Default: SumAreaSpaceChecker

**`WindowModeController.java`**
- Upravlja promenom režima prozora
- Kalkuliše faktor skaliranja

---

## 8. FLOW APLIKACIJE

### 8.1 Pokretanje Aplikacije

1. `AppCore.main()` se izvršava
2. Kreira se `ApplicationFramework`
3. Kreira se `MainFrame` (Singleton)
4. MainFrame inicijalizuje:
   - GraffRepository sa praznim Workspace-om
   - GraffTreeImplementation (JTree)
   - TabbedPane za slajdove
   - RightToolBar
   - ImageLoaderPanel
   - WindowModePanel
5. Aplikacija je spremna za rad

### 8.2 Kreiranje Projekta

1. Korisnik klikne na Workspace u stablu
2. Izabere "AddNode" akciju
3. FactoryGenerator kreira ProjectFactory
4. Kreira se novi Project čvor
5. GraffRepository validira i dodaje u Workspace
6. JTree se osvežava (Observer pattern)

### 8.3 Dodavanje Elementa na Slajd

1. Korisnik otvori slajd (double click u stablu)
2. Kreira se SlideView i SlideController za taj slajd
3. Korisnik izabere Add mode u toolbar-u
4. Klik na SlideView poziva AddMode.mousePressed()
5. AddMode proverava prostor (SpaceCheckerManager)
6. Ako ima prostora, kreira element
7. Dodaje element u slide (slide.addElement())
8. Slide obaveštava observere (notifyObservers())
9. SlideView se osvežava (repaint())

### 8.4 Undo Operacija

1. Korisnik pritisne Undo dugme (ili shortcut)
2. SlideController.undo() poziva UndoManager.undo()
3. UndoManager uzima poslednju komandu iz undo stack-a
4. Poziva komanda.undo()
5. Komanda vraća prethodno stanje
6. Slide obaveštava observere
7. SlideView se osvežava

---

## 9. PREDNOSTI IMPLEMENTACIJE

### 9.1 Arhitekturalne Prednosti

✅ **MVC Separation** - Jasno razdvojen model, view i controller
✅ **Design Patterns** - Praktična primena različitih obrazaca
✅ **Extensibility** - Lako dodavanje novih funkcionalnosti
✅ **Maintainability** - Čist, organizovan kod
✅ **Testability** - Komponente su nezavisne i testabilne

### 9.2 Funkcionalne Prednosti

✅ **Undo/Redo** - Kompletan mehanizam za vraćanje akcija
✅ **JSON Persistence** - Trajno čuvanje projekata
✅ **Template System** - Kreiranje i učitavanje šablona
✅ **Multiple Tool Modes** - Fleksibilan način rada
✅ **Zoom & Transformations** - Napredne 2D transformacije
✅ **Space Validation** - Inteligentna provera prostora

### 9.3 Korisničko Iskustvo

✅ **Intuitivni UI** - Jasna struktura i organizacija
✅ **Keyboard Shortcuts** - Brže korišćenje
✅ **Multi-selection** - Rad sa više elemenata istovremeno
✅ **Real-time Updates** - Automatsko osvežavanje prikaza
✅ **Flexible Window Modes** - Adaptacija na različite scenarije

---

## 10. MOGUĆA POBOLJŠANJA

### 10.1 Tehnička Poboljšanja
- [ ] Threading za asinhrone operacije (učitavanje slika)
- [ ] Caching mehanizam za thumbnail slike
- [ ] Optimizacija PixelMatrixSpaceChecker (može biti spor za velike slajdove)
- [ ] Memory cleanup za zatvorene tabove

### 10.2 Funkcionalna Poboljšanja
- [ ] Grupno poravnavanje elemenata
- [ ] Copy/Paste između različitih slajdova
- [ ] Undo history viewer
- [ ] Export u različite formate (PDF, PNG)
- [ ] Animation preview
- [ ] Drag & Drop slika direktno u slajd

---

## 11. ZAKLJUČAK

GRAFfito aplikacija predstavlja kompleksan desktop sistem za kreiranje prezentacija koji kombiniuje različite dizajn obrasce i principi dobre prakse u razvoju softvera. Implementacija koristi MVC arhitekturu sa jasnim razdvajanjem odgovornosti, što čini kod održivim i proširivim.

Ključni elementi implementacije uključuju:
- **Modularnu strukturu** - lako razumevanje i održavanje
- **Design patterns** - pravilno korišćenje obrazaca za rešavanje specifičnih problema
- **Observer pattern** - automatska sinhronizacija modela i view-a
- **Command pattern** - robusan Undo/Redo mehanizam
- **Strategy pattern** - fleksibilni načini rada aplikacije

Aplikacija ispunjava sve zahteve zadatka i pruža dodatne funkcionalnosti koje poboljšavaju korisničko iskustvo.

---

## 12. PRIMERI KORIŠĆENJA

### 12.1 Kreiranje Projekta i Dodavanje Slajdova

1. **Pokretanje aplikacije:**
   - Pokrenite `AppCore.main()` metodu
   - Otvara se glavni prozor sa praznim Workspace-om

2. **Kreiranje projekta:**
   - Desni klik ili izabrati Workspace u stablu
   - Klik na "AddNode" dugme
   - Kreira se novi Project sa automatskim imenom

3. **Dodavanje prezentacije:**
   - Selektovati Project
   - Klik na "AddNode"
   - Kreira se Presentation

4. **Dodavanje slajda:**
   - Selektovati Presentation
   - Klik na "AddNode"
   - Kreira se Slide

5. **Otvaranje slajda:**
   - Double click na Slide u stablu
   - Otvara se novi tab sa SlideView komponentom

### 12.2 Rad sa Elementima

1. **Učitavanje slika:**
   - U ImageLoaderPanel kliknuti "Učitaj Sliku"
   - Izabrati jednu ili više slika
   - Slike se prikazuju kao thumbnail-i
   - Klik na thumbnail selektuje sliku za dodavanje

2. **Dodavanje slike:**
   - Aktivirati Add mode u toolbar-u
   - Kliknuti na željeno mesto na slajdu
   - Slika se dodaje na tu poziciju

3. **Dodavanje teksta:**
   - Aktivirati Add mode
   - Ctrl+Click na slajdu
   - Izabrati "Text" u dijalogu
   - Uneti tekst
   - Tekst se dodaje na kliknutu poziciju

4. **Dodavanje logo-a:**
   - Aktivirati Add mode
   - Ctrl+Click na slajdu
   - Izabrati "Logo"
   - Logo se dodaje kao apstraktni trougao

5. **Selekcija i pomeranje:**
   - Aktivirati Select mode
   - Klik na element ili drag za multi-selekciju
   - Aktivirati Move mode
   - Drag selektovanih elemenata

6. **Rotacija:**
   - Selektovati element(e)
   - Kliknuti dugme za rotaciju 90° levo ili desno
   - Element se rotira oko svog centra

7. **Zoom:**
   - Scroll mouse wheel na slajdu
   - Ili koristiti + / - dugmad u toolbar-u
   - Reset zoom: kliknuti 🔍 dugme

8. **Brisanje:**
   - Selektovati element(e)
   - Pritisnuti Delete ili Backspace
   - Ili kliknuti DeleteNode u meniju

9. **Copy/Paste:**
   - Selektovati element(e)
   - Ctrl+C za kopiranje
   - Ctrl+V za lepljenje
   - Elementi se lepe sa malim offset-om

### 12.3 Čuvanje i Učitavanje

1. **Čuvanje projekta:**
   - File > Save (ako je projekat već sačuvan)
   - Ili File > Save As (bira se nova lokacija)
   - Projekat se čuva kao JSON fajl

2. **Učitavanje projekta:**
   - File > Open
   - Izabrati JSON fajl
   - Projekat se učitava i prikazuje u stablu

3. **Rad sa šablonima:**
   - File > Save Template
   - Projekat se čuva u resources folder
   - File > Load Template
   - Učitava se šablon i dodaje u trenutni projekat

### 12.4 Promena Režima Radnog Prostora

- Radio buttons u gornjem toolbar-u:
  - **Normal** - standardna veličina
  - **Fullscreen** - ceo ekran
  - **Small** - polovina normalne veličine

---

## 13. ODGOVORI NA ČESTA PITANJA

### P: Kako funkcioniše Observer pattern u vašoj aplikaciji?
**O:** Observer pattern se koristi na više mesta:
- Slide obaveštava SlideView kada se promeni (dodavanje/brisanje elemenata)
- GraffRepository obaveštava JTree kada se promeni struktura
- Presentation obaveštava RightPanelObserver o promenama slajdova
- To omogućava automatsko osvežavanje UI-ja bez direktne zavisnosti između modela i view-a

### P: Zašto koristite Command pattern za Undo/Redo?
**O:** Command pattern enkapsulira svaku akciju kao objekat sa execute() i undo() metodama. To omogućava:
- Jednostavno dodavanje novih komandi
- Stack mehanizam za undo/redo
- Mogućnost serializacije komandi (za budućnost)
- Testabilnost - svaka komanda je nezavisan test slučaj

### P: Kako se rešava problem preklapanja elemenata pri proveri prostora?
**O:** Implementirana su dva pristupa:
1. **SumAreaSpaceChecker** - brz ali ne uzima u obzir preklapanja
2. **PixelMatrixSpaceChecker** - sporiji ali precizniji, koristi binarnu matricu piksela
Strategy pattern omogućava dinamičko menjanje pristupa bez menjanja koda koji koristi proveru.

### P: Kako funkcioniše rotacija sa matricama transformacije?
**O:** Java Graphics2D koristi AffineTransform objekte. Za rotaciju:
- Kreira se rotaciona matrica oko centra elementa
- Matrica se primenjuje na Graphics2D kontekst
- Sve dalje iscrtavanje je rotirano
- Element zadržava svoj ugao rotacije u modelu

### P: Zašto koristite Singleton pattern za Manager klase?
**O:** Singleton osigurava:
- Jednu instancu kritičnih objekata (npr. SlideControllerManager)
- Globalnu tačku pristupa bez prosleđivanja referenci
- Kontrolu nad inicijalizacijom
- Sprečava nepotrebno kreiranje više instanci

### P: Kako funkcioniše JSON serijalizacija?
**O:** Jackson biblioteka automatski konvertuje Java objekte u JSON:
- Anotacije kontrolišu šta se serijalizuje
- Workspace struktura se čuva rekurzivno
- Prilikom učitavanja, rekonstruišu se objekti i parent relacije
- View komponente se ne čuvaju jer nisu deo modela

### P: Kako se rešava fokus na keyboard shortcuts?
**O:** SlideView je postavljen kao focusable (`setFocusable(true)`) i prima fokus kada se tab aktivira. Keyboard listener je registrovan na SlideView, pa shortcuts (Ctrl+C, Ctrl+V, Delete) rade samo kada je slajd fokusiran.

---

## 14. TEHNIČKE SPECIFIKACIJE

### 14.1 Sistem Zahteva

- **Java verzija:** 21 (JDK 21)
- **GUI Framework:** Java Swing
- **JSON Biblioteka:** Jackson 2.15.2
- **Build Tool:** Maven
- **OS:** Cross-platform (Windows, Linux, macOS)

### 14.2 Struktura Fajlova

```
projekat/
├── pom.xml                          # Maven konfiguracija
├── src/main/java/
│   ├── raf/graffito/dsw/           # Glavni paket
│   │   ├── AppCore.java            # Main entry point
│   │   ├── core/                   # Core funkcionalnost
│   │   ├── controller/             # Kontroleri i akcije
│   │   └── gui/swing/              # UI komponente
│   └── app/                        # Aplikacioni paket
│       ├── model/                  # Model klase
│       ├── view/                   # View klase
│       └── observer/               # Observer implementacija
└── resources/                      # Resursi (šabloni)
```

### 14.3 Dependencies

```xml
- lombok 1.18.42          # Anotacije za gettere/settere
- jackson-databind 2.15.2  # JSON serijalizacija
```

---

## 15. TESTIRANJE I PROVERA FUNKCIONALNOSTI

### 15.1 Test Scenariji

**✅ Testiranje hijerarhije:**
- Kreiranje Workspace > Project > Presentation > Slide
- Validacija da Project ne može biti direktan child Workspace-om ako već postoji
- Provera duplikata imena

**✅ Testiranje dodavanja elemenata:**
- Dodavanje Image, Text, Logo elemenata
- Provera da se elementi pravilno iscrtavaju
- Provera rotacije i transformacija

**✅ Testiranje Undo/Redo:**
- Izvršavanje akcije
- Undo vraća prethodno stanje
- Redo ponovo izvršava akciju
- Stack se ispravno upravlja

**✅ Testiranje JSON:**
- Čuvanje projekta
- Učitavanje projekta
- Provera da su svi čvorovi i elementi učitani
- Provera parent-child relacija

**✅ Testiranje Tool Modes:**
- Prebacivanje između modova
- Svaki mod ispravno reaguje na mouse evente
- Selekcija i manipulacija rade kako treba

**✅ Testiranje Zoom:**
- Mouse wheel zoom
- Zoom dugmad
- Zoom limits (0.25x - 4.0x)
- Zoom se pravilno primenjuje na transformacije

**✅ Testiranje provere prostora:**
- Dodavanje elementa kada ima dovoljno prostora
- Dodavanje elementa kada NEMA dovoljno prostora (upozorenje)
- Oba načina provere (Sum i Pixel Matrix)

**✅ Testiranje režima prozora:**
- Prebacivanje između Normal/Fullscreen/Small
- Provera skaliranja u različitim režimima

### 15.2 Poznati Problemi i Ograničenja

1. **Performance:** PixelMatrixSpaceChecker može biti spor za velike slajdove sa mnogo elemenata
2. **Memory:** SlideController objekti se ne brišu kada se zatvori tab (moguć optimizacija)
3. **Image Loading:** Nema async učitavanje slika (može zamrznuti UI za velike slike)
4. **Zoom Limits:** Fiksni limiti (0.25x - 4.0x) mogu biti prilagođeni korisničkim preferencama

---

## 16. FINALNA PROVERA

### ✅ Sve Funkcionalnosti Implementirane:

- [x] Hijerarhijska struktura (Workspace > Project > Presentation > Slide)
- [x] Dodavanje različitih elemenata (Image, Text, Logo)
- [x] Tool modes (Add, Select, Move, Resize, Rotate, Zoom)
- [x] Undo/Redo mehanizam
- [x] JSON serijalizacija (Save, Save As, Open)
- [x] Template sistem (Save/Load Template)
- [x] Copy/Paste funkcionalnost
- [x] Provera dostupnog prostora (2 načina)
- [x] Promena režima prozora (Normal/Fullscreen/Small)
- [x] Image loader panel sa thumbnail-ima
- [x] Keyboard shortcuts (Delete, Ctrl+C, Ctrl+V)
- [x] Zoom sa mouse wheel i dugmadima
- [x] Rotacija 90° levo/desno
- [x] Observer pattern za automatsko osvežavanje
- [x] MVC arhitektura
- [x] Design patterns (Observer, Command, Strategy, Factory, Singleton)

---

**NAPOMENA:** Ovaj dokument je pripremljen za usmenu odbranu projekta GRAFfito i pokriva sve ključne aspekte implementacije. Projekat je potpuno funkcionalan i testiran.

