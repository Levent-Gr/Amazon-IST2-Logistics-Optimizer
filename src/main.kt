import kotlin.random.Random
import kotlin.math.ceil

fun analizEt(veri: List<String>) {

    val paketOlculeri = mapOf(
        "LM1" to listOf(250, 200, 50),
        "LM3" to listOf(350, 250, 50),
        "NM1" to listOf(250, 150, 100),
        "BP2" to listOf(250, 200, 120),
        "NM3" to listOf(300, 250, 150),
        "NM4" to listOf(400, 350, 150),
        "DM1" to listOf(219, 155, 70),
        "DM2" to listOf(250, 165, 70),
        "DM3" to listOf(282, 205, 70),
        "DM4" to listOf(312, 250, 70),
        "U73" to listOf(344, 254, 120),
        "EUO" to listOf(228, 160, 102),

        "EUP" to listOf(305, 228, 102),
        "EUR" to listOf(305, 228, 183),
        "EUS" to listOf(406, 305, 102),
        "SIOC" to listOf(300, 200, 100),
        "EUV" to listOf(550, 350, 100),

        "EUU" to listOf(406, 305, 203),
        "U10" to listOf(455, 340, 265),
        "EU2" to listOf(508, 408, 300),
        "MD9" to listOf(610, 460, 410)
    )

    val kucuk = listOf("LM1","LM3","NM1","BP2","NM3","NM4","DM1","DM2","DM3","DM4","U73","EUO")
    val orta  = listOf("EUP","EUR","EUS","SIOC","EUV")
    val buyuk = listOf("EUU","U10","EU2","MD9")
    val kategoriSiralama = listOf(kucuk, orta, buyuk)


    val shuttleHacim = 1200L * 800L * 1600L


    // hangı paketten kac tane var onun bılgısı map sekılde tutuyoruz
    
    /*
     {
  		"LM1": 3,
  		"DM2": 1,
  		"NM3": 2
	}
     */
    val adetler = mutableMapOf<String, Int>()


    // adetler map ıne key olarak paket kodu alıyoruz ve ona göre ekleme yapıyoruz
    for (paket in veri) {
        val mevcutAdet = adetler[paket] ?: 0
        adetler[paket] = mevcutAdet + 1
    }

    
    
    val toplamPaket = veri.size
    if (toplamPaket == 0) {
        println("Girdi bulunamadı.")
        return
    }

    println("Toplam Paket Sayısı: $toplamPaket")
    println("-----")

    /*
     
     "kategorı listesi" bu sekılde alıyor fonksıyona "kucuk lıste" degerını atarsak onun uzerınden ılerleme
     devam eder.Herhangı bir eleme yapmasına gerek yok.Fonksiyona "kucuk listesi"ni gonderırsek o katagorının paket sayılarını 
     toplar.
    val kucuk = listOf("LM1","LM3","NM1","BP2","NM3","NM4","DM1","DM2","DM3","DM4","U73","EUO")
    val orta  = listOf("EUP","EUR","EUS","SIOC","EUV")
    val buyuk = listOf("EUU","U10","EU2","MD9") 
   
     */
    
    fun kategoriYazdir(baslik: String, kategori: List<String>) {

        var kategoriToplam = 0
        for (p in kategori) {
            kategoriToplam += adetler[p] ?: 0
        }

        
        if (kategoriToplam == 0) return

        
        //kategorının yuzdelıgı ekrana yazıyoruz
        val yuzde = kategoriToplam.toDouble() / toplamPaket * 100
        println("$baslik (Toplam: $kategoriToplam adet, %.2f%%)".format(yuzde))
        println("--------------------")

        
       
        
        //Kategori listesi içindeki her paket kodunu "adetler" map ındekı "key (string paket kodu)" ıle 
        //eşleyip "paketAdet" ıcıne sayını atıyoruz (value sayısı oluyor map oldugu ıcın). En son yuzdelık hesabı yapıyoruz 
        for (p in kategori) {
            val paketAdet = adetler[p] ?: 0
            if (paketAdet > 0) {
                val pYuzde = adet.toDouble() / toplamPaket * 100
                println("$p: $adet adet (%.2f%%)".format(pYuzde))
            }
        }
        println()
    }

    
    

 
    
    kategoriYazdir("Küçük Paketler", kucuk)
    kategoriYazdir("Orta Paketler", orta)
    kategoriYazdir("Büyük Paketler", buyuk)

    
    
 
    
    
    /*
      
   "tumPaketler" ı bu sekılde lısteleme yapıyoruz paket kodu ve hacmı
   en boy yukseklık carpıp kac adet varsa onları "tumPaketler" lıstesıne atıyoruz "map" şeklinde
   Bu degerlerı (en boy yukseklık) her seferınde hesaplamadan yapabılırım daha faydalı olur 
   
   
       Pair(
  	paketAdi = "LM1",
  	hacim = 1200
	)
    
    [
 	("LM1", 1200), 
 	("EU2", 800),
 	("LM1", 1200),
 	("MD9", 2000),
 	...
	]
    
    */
    val tumPaketler = mutableListOf<Pair<String, Long>>()

    
    //Her  paket tıpını alıyoruz ve adetı kadar lısteye eklıyoruz eklemeden once tum hacmını hesaplıyoruz.
    
    for ((paketAdi, adet) in adetler) {

        val olcu = paketOlculeri[paketAdi] ?: continue
        val hacim = olcu[0].toLong() * olcu[1] * olcu[2]

        var i = 0
        while (i < adet) {
            tumPaketler.add(Pair(paketAdi, hacim))
            i++
        }
    }

    //sırayı karıstırmak ıcın random
    val random = Random(1234)
    tumPaketler.shuffle(random)

    
    
   	// kabaca shuttle sayısı
    var toplamHacim = 0L
    for (p in tumPaketler) {
        toplamHacim += p.second
    }

    var shuttleSayisi = ceil(toplamHacim.toDouble() / shuttleHacim).toInt()
    if (shuttleSayisi < 1) shuttleSayisi = 1

   
    
    //Shutle class olusturulup yapılması daha uygun olur IDE degişirse
    val shuttleDoluluk = MutableList(shuttleSayisi) { 0L }
    val shuttleIcerik = MutableList(shuttleSayisi) { mutableMapOf<String, Int>() }
    val shuttlePaketSayisi = MutableList(shuttleSayisi) { 0 }

    
    
  	//Limitler
    val paketLimitleri = mapOf(
        "U10" to 12,
        "EU2" to 10,
        "MD9" to 6
    )

    
    
    
    
    
    
    
  //Dagıtım algorıtması
for ((paketAdi, hacim) in tumPaketler) {

    /*
     	"Kapasiteye" bak   ve   "Limite" bak    ıkısıde uygunsa "uygunShuttlelar" a EKLE
        Sartalardan gecemezlerse "uygunShuttlelar" bos kalır ve alta gırıp uygun shutlle acılır
    */
    val uygunShuttlelar = mutableListOf<Int>()

    for (i in shuttleDoluluk.indices) {

        // Kapasite kontrolü
        if (shuttleDoluluk[i] + hacim > shuttleHacim) continue 

        // Paket limit kontrolü
        val limit = paketLimitleri[paketAdi]
        val mevcut = shuttleIcerik[i][paketAdi] ?: 0
        if (limit != null && mevcut >= limit) continue

        // Bu shuttle uygundur
        uygunShuttlelar.add(i)
    }

    /*
    yukarıda sartları gecemez ısek yenı shuttle acıyoruz en az 1 shuttle uygun varsa 
    buraya gırmeden alta gırıp devam eder 1 shuttle uzerınden degerlendırme yaparız
    */
    if (uygunShuttlelar.isEmpty()) {
        shuttleDoluluk.add(hacim)
        shuttlePaketSayisi.add(1)
        shuttleIcerik.add(mutableMapOf(paketAdi to 1))
        continue
    }

 	//en az dolu olan shuttle ı bul 
    var enAzDolulukShuttle = Long.MAX_VALUE // ılk deger max olsun kıyaslama en yuksekden baslasın
    for (i in uygunShuttlelar) {
        if (shuttleDoluluk[i] < enAzDolulukShuttle) {
            enAzDolulukShuttle = shuttleDoluluk[i]
        }
    }

    //ağırlık ıcın secım havuzu "index"  uzerınden ağırlık verıyoruz paket sıgmayan shuttlar kontrol edılmez
    val secimHavuzu = mutableListOf<Int>()

    for (i in uygunShuttlelar) {

        val dolulukFarki = shuttleDoluluk[i] - enAzDolulukShuttle

     	//doluluk farkı
        val agirlik = (100 - (dolulukFarki * 100 / shuttleHacim))
            .toInt()
            .coerceAtLeast(1)

        //agırlık sayısına gore havuza ekleme
        var j = 0
        while (j < agirlik) {
            secimHavuzu.add(i)
            j++
        }
    }

	
    //agırlık hesabına gore random shuttle sec
    val secilenShuttle = secimHavuzu[random.nextInt(secimHavuzu.size)]


    //shuttle a yerlestırme işlemi
    shuttleDoluluk[secilenShuttle] += hacim
    shuttlePaketSayisi[secilenShuttle] += 1
    shuttleIcerik[secilenShuttle][paketAdi] = (shuttleIcerik[secilenShuttle][paketAdi] ?: 0) + 1
}


    
     //Çıktı
   
    for (i in shuttleDoluluk.indices) {
        val yuzde = shuttleDoluluk[i].toDouble() / shuttleHacim * 100
        println("Shuttle #${i + 1}")
        println("Paket Sayısı: ${shuttlePaketSayisi[i]}")
        println("Doluluk: %.2f%%".format(yuzde))
        println("İçerik:")

        for (kategori in kategoriSiralama) {
            for (paket in kategori) {
                val adet = shuttleIcerik[i][paket] ?: 0
                if (adet > 0) {
                    println("  $paket : $adet  adet")
                }
            }
        }
        println("-----")
    }
}


fun hazirlaVeriListesi(): List<String> {
    val veriString = """
DM2
LM1
NM3
DM1
EUO
LM3
DM4
NM1
U73
BP2
NM4
DM3
    """.trimIndent()

    val sonuc = mutableListOf<String>()
    val satirlar = veriString.split("\n")

    for (s in satirlar) {
        val temiz = s.trim()
        if (temiz.isNotEmpty()) {
            sonuc.add(temiz)
        }
    }
    return sonuc
}

fun main() {
    val veri = hazirlaVeriListesi()
    analizEt(veri)
}
