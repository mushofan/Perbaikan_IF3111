# Perbaikan_IF3111
Hasil eksplorasi dan tutorial terkait Runtime Request Permission, Doze, dan App Standby

Runtime Permission Request
Mulai dari Android 6.0, pemberian akses untuk aplikasi dilakukan ketika aplikasi telah diinstal dan sedang dijalankan, bukan ketika pertama kali aplikasi diinstal. Dengan demikian, pengguna lebih memiliki kontrol terhadap aplikasi. Sebagai contoh, aplikasi meminta akses terhadap lokasi perangkat, pengguna dapat memberikan dan mencabut akses sewaktu-waktu. Pengguna dapat mencabut akses yang diberikan ke suatu aplikasi melalui menu Settings >> Application, dan dapat memberikan akses kembali ketika menjalankan aplikasi tersebut.

Doze
Doze adalah sistem penghematan baterai baru yang diterapkan di Android 6.0 (Marshmallow). Perangkat akan memasuki mode Doze apabila perangkat benar-benar dalam keadaan idle, tidak dalam keadaan bergerak, dan layar dalam keadaan mati. Doze akan menghidupkan perangkat android secara berkala, sehingga semua tugas yang ditunda dapat dijalankan saat itu, termasuk alarm. 
Hanya ada beberapa kasus yang dapat “keluar” dari Doze, contohnya ketika perangkat android berada di daerah dengan sinyal jaringan yang lemah, dan aplikasi alarm yang menggunakan metode setAndAllowWhileIdle() atau setExactAndAllowWhileIdle(), bukan set() dan setExact() biasa. Selain itu, aplikasi dengan tingkat pemberitahuan yang darurat, termasuk sms dan telepon, masih dapat ditampilkan.

App Standby
App Standby digunakan untuk mencegah penggunaan daya berlebih oleh aplikasi yang jarang digunakan. Mode ini ditambahkan dari Android 6.0 (Marshmallow). App Standby memiliki tujuan yang sama dengan Doze, yakni menghemat pemakaian daya baterai oleh perangkat android. Perbedaan App Standby dari Doze adalah App Standby melakukan penghematan pemakaian daya dengan mengatur jalannya aplikasi yang ada. Aplikasi yang memasuki mode standby masih dapat berjalan, namun tidak dapat berjalan terlalu sering. Dengan mode ini, aplikasi dicegah dari berjalan di background. Aplikasi yang jarang digunakan, baik oleh pengguna maupun oleh aplikasi lain, secara otomatis akan dipindahkan ke mode standby. Ketika aplikasi tersebut digunakan oleh pengguna, secara otomatis aplikasi tersebut dipindahkan dari mode standby.

Mode standby hanya akan diaktifkan ketika perangkat android menggunakan daya dari baterai (discharging mode), apabila perangkat android dalam keadaan mengisi baterai (charging), mode standby  tidak diaktifkan, atau dengan kata lain semua aplikasi yang pada awalnya berada pada mode standby akan berjalan secara normal. Mode standby ini dapat mengurangi dampak dari bloatware (pre-installed apps) yang dipasang oleh perakit perangkat android atau oleh penyedia jaringan.


Aplikasi ini mencoba untuk mengetahui dampak dari penerapan Runtime Request Permission, Doze, dan App Standby terhadap aplikasi ini. Untuk melakukan pengujian terhadap Runtime Request Permission, digunakan menu Camera. Pada menu ini, aplikasi meminta izin dari pengguna untuk menggunakan kamera pada perangkat android pengguna. 

Untuk pengujian terhadap Doze, aplikasi ini menggunakan menu Alarm, yang akan membuka aplikasi alarm dan mencoba menghidupkan alarm tersebut. Pada source code, disediakan 2 metode set alarm yang dapat digunakan, yakni metode set() dan metode setAndAllowWhileIdle(). Fungsi set() akan terblokir oleh Doze, sehingga alarm tidak akan muncul, sedangkan untuk setAndAllowWhileIdle() tetap akan memunculkan alarm walau perangkat android masuk ke mode Doze.

Selanjutnya, untuk pengujian terhadap App Standby, digunakan menu Periodic Alarm, yang akan secara periodik mengaktifkan background task. Pengujian dapat dilakukan dengan memaksa aplikasi ini untuk masuk ke mode App Standby melalui perintah

$ adb shell am set-inactive <packageName> true

kemudian aplikasi dikeluarkan dari metode tersebut dengan perintah

$ adb shell am set-inactive <packageName> false
$ adb shell am get-inactive <packageName>

