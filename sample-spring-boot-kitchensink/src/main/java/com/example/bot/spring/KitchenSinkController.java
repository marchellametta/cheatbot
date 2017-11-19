/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example.bot.spring;

import cleverbot.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LineMessageHandler
public class KitchenSinkController {

String[] news = {
        "NEW DELHI, KOMPAS.com - Seorang calon mahasiswi di India diminta melepaskan bra yang dia kenakan oleh pengelola sekolah karena dikhawatirkan akan mencontek dalam ujian.\n" +
"\n" +
"Pemerintah biasanya memberlakukan aturan ketat soal pakaian untuk mencegah para peserta ujian masuk sekolah kedokteran mencontek.\n" +
"\n" +
"Calon mahasiswi itu, yang tak disebutkan namanya, mengatakan kepada media bahwa dia datang ke lokasi ujian di negara bagian Kerala saat detektor logam berbunyi saat dia diperiksa.\n" +
"\n" +
"\"Saat itu, mereka mengatakan jika saya tak melepaskan pakaian dalam maka saya tak boleh ikut ujian karena kancing bra terbuat dari logam,\" kata siswi itu.\n" +
"\n" +
"\"Jadi terpaksa saya melepas bra dan memberikannya kepada ibu yang menunggu di luar gedung,\" tambah gadis itu kepada stasiun televisi NDTV, Senin (8/5/2017).\n" +
"\n" +
"Kepada harian The Indian Times, gadis itu mengaku sangat malu dan kehilangan kepercayaan diri karena dipaksa melepaskan bra di depan banyak orang.\n" +
"\n" +
"Sementara itu, seorang peserta ujian lain dipaksa melepaskan kancing celana jins yang dikenakan setelah memicu alarm di detektor logam.\n" +
"\n" +
"Masalah ini kemudian sampai ke telinga kepala sekolah kedokteran yang diperebutkan para siswa itu.\n" +
"\n" +
"Dia mengakui tak mengetahui insiden \"lepas bra\" itu dan hanya mengatakan pihaknya hanya menjalankan aturan yang sudah ditetapkan.\n" +
"\n" +
"\"Kami menerima perintah yang jelas bahwa jika detektor logam berbunyi, orang itu tak diizinkan masuk ke dalam ruangan,\" ujar Jalaluddin K.\n" +
"\n" +
"\"Saat detektor logam berbunyi, maka siswa harus melepaskan apapun yang mereka kenakan atau bawa,\" tambah dia.\n" +
"\n" +
"Berbagai cara digunakan para calon mahasiswa di India untuk lulus tes seleksi mulai dari mencontek hingga membeli lembar soal dan jawaban.\n" +
"\n" +
"Pada 2015, ratusan orang di negara bagian Bihar ditahan karena memanjat tembok sekolah untuk memberikan lembar catatan kepada kerabat mereka yang sedang menjalankan ujian.",
        
        
        "KAIRO, KOMPAS.com - Pemerintah Mesir awal pekan ini mengumumkan undang-undang baru yang memberi hukuman berat bagi mereka yang mencontek dalam ujian negara.\n" +
"\n" +
"Undang-undang baru, yang sudah diratifikasi Presiden Abdel Fattah al-Sisi pekan lalu itu, ditujukan untuk memerangi kebiasaan mencontek.\n" +
"\n" +
"Undang-undang ini juga diharapkan bisa mengendalikan semua proses ujian negara di seluruh sekolah di Mesir.\n" +
"\n" +
"Berdasarkan undang-undang baru ini, siapa saja yang ketahuan mencontek atau membocorkan jawaban ujian akan dijatuhi hukuman penjara dua tahun.\n" +
"\n" +
"Selain itu, mereka juga akan dijatuhi hukuman denda maksimal 200.000 pound Mesir atau Rp 147 juta.\n" + 
            "\"Sementara para pelajar yang mencoba mencontek atau melakukan pelanggaran lain akan dilarang mengikuti semua jenis ujian,\" ujar Reda Hegazy, kepala dinas pendidikan publik Mesir. Dalam aturan lama, para pelajar yang ketahuan mencontek hanya akan dilarang mengikuti ujian di mata pelajaran tertentu saja.\n" +
"\n" +
"Tahun lalu, jawaban dari sejumlah mata pelajaran dalam ujian nasional Mesir dibocorkan secara online.\n" +
"\n" +
"Alhasil, pemerintah harus menunda pelaksanaan ujian nasional dan menggelar investigasi setelah muncul kecurigaan kebocoran berasal dari kementerian pendidikan.\n" +
"\n" +
"Saat itu, pengelola grup Facebook yang membocorkan jawaban ujian ditangkap dan diajukan ke pengadilan.",
        
        
        "Jakarta, Kompas - Sejumlah perguruan tinggi negeri dan swasta membuat komitmen bersama untuk tidak melakukan kegiatan mencontek dan plagiat di kampus. Sudah saatnya perguruan tinggi menjunjung tinggi norma dan budaya akademik serta nilai-nilai kejujuran.\n" +
"\n" +
"Komitmen bersama ini dilakukan rektor/pimpinan perguruan tinggi, direktur politeknik, ketua sekolah tinggi, dan koordinator perguruan tinggi swasta yang didukung Direktorat Jenderal Pendidikan Tinggi Kementerian Pendidikan Nasional.\n" +
"\n" +
"â€Harus kita akui, di kampus masih terjadi kegiatan mencontek dan plagiat. Bukan cuma mahasiswa, guru besar juga ada yang melakukan. Karena itu, kejujuran akademik harus kembali ditegakkan,â€ kata Musliar Kasim, Ketua Majelis Rektor Perguruan Tinggi Negeri, Kamis (12/5).\n" +
"\n" +
"Direktur Jenderal Pendidikan Tinggi Kemdiknas Djoko Santoso mengatakan, kampus bukan hanya bertujuan mencetak lulusan yang cerdas. â€Selain kecerdasan, kampus harus mampu berperan dalam mencetak generasi muda yang andal, bermoral, dan berkarakter baik,â€ ujarnya.\n" +
"\n" +
"Rochmat Wahab, Rektor Universitas Negeri Yogyakarta, mengatakan, plagiarisme sebagai kejahatan berat di kampus dan harus dihindari, sejak awal sudah disosialisasikan kepada mahasiswa. Imbauan untuk tidak melakukan plagiarisme karya ilmiah mulai dikenalkan sejak masa orientasi mahasiswa baru.\n" +
"\n" +
"â€Kini bukan cuma imbauan. Saat membuat karya ilmiah, dosen dan mahasiswa harus membuat surat pernyataan bahwa karyanya asli bukan hasil plagiat,â€ kata Wahab.\n" +
"\n" +
"Muhammad Anis, Wakil Rektor Bidang Akademik dan Kemahasiswaan Universitas Indonesia (UI), mengatakan, perguruan tinggi harus membuat aturan tegas dan sanksi bagi pelaku plagiarisme, serta disosialisasikan sejak awal pada semua kalangan,\n" +
"\n" +
"â€Di kampus kami, sudah ada sistem yang terus dikembangkan untuk melawan antimencontek dan plagiarisme. Tiap ujian, mahasiswa harus memahami tata tertib tentang ketentuan tidak mencontek. Jika sejak awal direncanakan mencontek, mahasiswa dapat nilai E untuk semua mata kuliah pada semester tersebut,â€ ujar Anis.\n" +
"\n" +
"Adapun untuk menghindari plagiarisme mahasiswa dalam menyusun skripsi, UI menerapkan aturan, mahasiswa paling sedikit melakukan 10 kali tatap muka dengan dosen pembimbing. â€Setiap pertemuan, apa yang dibahas mahasiswa dan dosen pembimbing itu tercatat secara online,â€ ujarnya. (ELN)",
        
        
        "BANGKOK, KOMPAS.com -  Pengelola Universitas Rangsit di Bangkok, Thailand memergoki sejumlah pelajar yang melakukan aksi mencontek menggunakan peralatan canggih saat ujian masuk di Fakultas Kedokteran.\n" +
"\n" +
"Para pelajar itu tepergok memakai kamera tersembunyi yang dipasang di kacamata dan terhubung dengan \"smart watch\".\n" +
"\n" +
"Sejumlah pengguna media sosial, Senin (9/5/2016), kemudian membandingkan temuan ini dengan aksi serupa dalam film laga yang dibintangi Tom Cruise, Mission Impossible.\n" +
"\n" +
"Temuan ini terungkap ke publik setelah Arthit Ourairat, yang menjabat sebagai Rektor di Universitas Rangsit mengunggah sejumlah foto bergambar \"alat contek canggih\" ke akun Facebook-nya Minggu sore.\n" +
"\n" +
"Dengan foto itu, Arthit juga mengumumkan, ujian masuk penerimaan mahasiswa baru tersebut telah dibatalkan, menyusul terungkapkan skenario curang tersebut. \n" +
"\n" +
"Disebutkan, tiga pelajar menggunakan kacamata berkamera yang bisa mentransmisikan gambar kepeda sekolompok orang yang hingga kini belum teridentifikasi.\n" +
"\n" +
"Kemudian, kelompok orang tersebut akan mengirimkan jawaban untuk soal ujian tersebut melalui piranti 'jam tangan pintar'.\n" +
"\n" +
"Arthit menyebut, masing-masing pelajar tersebut harus membayar 23.000 dollar AS atau sekitar Rp 300 juta untuk mendapat jasa layanan 'joki' termasuk piranti dan jawaban soal.\n" +
"\n" +
"\"Tim tersebut mengirimkan jawaban secara 'real time',\" kata dia.\n" +
"\n" +
"Saluran televisi Thailand, Channel 3 menyebutkan, ketiga calon mahasiswa itu kemudian dimasukkan ke dalam daftar hitam dan tidak bisa melanjutkan proses seleksi di tempat itu.\n" +
"\n" +
"\"Kami ingin hal ini diketahui publik, agar mereka waspada, apalagi terkait dengan ujian masuk fakultas kedokteran yang diincar oleh banyak peminat, namun daya tampungnya tak sebanyak itu,\" kata Arthit.\n" +
"\n" +
"Unggahan sang rektor menjadi viral dalam waktu yang tak lama. Banyak pengguna media sosial yang memuji upaya yang disebut cerdik itu, tapi tak kurang juga yang mengecam aksi curang mereka. \n" +
"\n" +
"\"Jika mereka lulus dan lalu diwisuda di kemudian hari, maka akan ada dokter-dokter ilegal yang bekerja di sekitar kita,\" demikian ditulis oleh salah satu pengguna Facebook.\n" +
"\n" +
"Komentar lain yang muncul, \"hebat, seperti menonton film Hollywood, Mission: Impossible.\"",
        
        "NEW DELHI, KOMPAS.com â€” Para mahasiswa sering kali berusaha keras untuk menuntut hak mereka. Namun, akhir-akhir ini terjadi hal menarik.\n" +
"\n" +
"Sebagian mahasiswa di India membicarakan hak mereka untuk melakukan kecurangan mencontek pada ujian universitas.\n" +
"\n" +
"\"Ini adalah hak demokratis kami!\" kata pria kurus bernama Pratap Singh kepada wartawan BBC, Craig Jeffrey.\n" +
"\n" +
"Sambil minum chai atau teh di luar universitasnya di negara bagian Uttar Pradesh, Pratap mengatakan bahwa mencontek adalah hak orang sejak lahir.\n" +
"\n" +
"Korupsi pada sistem ujian universitas adalah suatu hal yang umum di India. Warga kaya dapat menyuap agar sukses menjalani ujian.\n" +
"\n" +
"Sekelompok anak muda bahkan menjadi perantara bagi para mahasiswa dan pejabat sekolah. Ada juga kelompok mahasiswa yang terkenal karena kedekatan mereka dengan dunia politik.\n" +
"\n" +
"Jadi, jika pihak-pihak yang mempunyai uang dan kekuatan politik diizinkan untuk curang, mengapa mahasiswa miskin tidak boleh melakukan hal yang sama?\n" +
"\n" +
"\"Sistem universitas di India tengah berada dalam krisis,\" kata Singh sambil menyulut rokoknya.\n" +
"\n" +
"\"Kecurangan terjadi di semua level. Mahasiswa menyuap untuk pendaftaran dan mendapatkan hasil yang baik. Para mahasiswa membayar para profesor untuk menulis disertasi mereka. Para profesor juga curang, mereka menulis artikel di jurnal-jurnal palsu,\" lanjut Singh.\n" +
"\n" +
"\"Saya tak tahu apakah harus menangis atau tertawa. Peralatan di laboratorium sains kami sudah layak dibuang. Ini seperti universitas Inggris tahun 1950-an,\" lanjut dia.\n" +
"\n" +
"Keluhan yang sama datang dari Pinki Singh, seorang mahasiswi sebuah universitas ternama di Uttar Pradesh.\n" +
"\n" +
"\"Saya akan bicara tentang pendidikan di India. Kurikulumnya buruk, dan saat para dosen tak mau mengajar dengan benar, Anda harus mendapatkan bimbingan privat atau hanya menghafal buku teks,\" ujar Pinki.\n" +
"\n" +
"\"Jadi, tak ada gunanya belajar dengan benar. Anda hanya harus membeli salah satu buku contekan di pasar, dan pelajari jawabannya,\" lanjut Pinki.\n" +
"\n" +
"\"Pada tahun pertama saya belajar sejarah, saya coba belajar dengan benar. Namun, senior saya mengatakan, beli saja buku contekan,\" lanjut Pinki.\n" +
"\n" +
"Kualitas sejumlah universitas swasta tak jauh berbeda. Pinki menyebut sebuah universitas yang baru didirikan.\n" +
"\n" +
"Calon mahasiswa membayar mahal untuk mendaftar lewat internet. Selanjutnya, situs universitas itu menghilang dari dunia maya.\n" +
"\n" +
"\"Universitas itu tak memiliki lokasi fisik dan tak ada jejak saat dicari di dunia maya. Universitas itu menghilang begitu saja,\" kata Pinki.\n" +
"\n" +
"Lalu apa solusinya? Saat unjuk rasa pro-mencontek digelar di Uttar Pradesh pada awal 1990-an, menteri utama Uttar Pradesh menuruti desakan mahasiswa, dan menghapus undang-undang anti-mencontek."};
    

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private boolean isActive = true;
    Cleverbot c;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    /*@EventMapping
    public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
        handleSticker(event.getReplyToken(), event.getMessage());
    }

    @EventMapping
    public void handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        LocationMessageContent locationMessage = event.getMessage();
        reply(event.getReplyToken(), new LocationMessage(
                locationMessage.getTitle(),
                locationMessage.getAddress(),
                locationMessage.getLatitude(),
                locationMessage.getLongitude()
        ));
    }

    @EventMapping
    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws IOException {
        // You need to install ImageMagick
        handleHeavyContent(
                event.getReplyToken(),
                event.getMessage().getId(),
                responseBody -> {
                    DownloadedContent jpg = saveContent("jpg", responseBody);
                    DownloadedContent previewImg = createTempFile("jpg");
                    system(
                            "convert",
                            "-resize", "240x",
                            jpg.path.toString(),
                            previewImg.path.toString());
                    reply(((MessageEvent) event).getReplyToken(),
                          new ImageMessage(jpg.getUri(), jpg.getUri()));
                });
    }

    @EventMapping
    public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) throws IOException {
        handleHeavyContent(
                event.getReplyToken(),
                event.getMessage().getId(),
                responseBody -> {
                    DownloadedContent mp4 = saveContent("mp4", responseBody);
                    reply(event.getReplyToken(), new AudioMessage(mp4.getUri(), 100));
                });
    }

    @EventMapping
    public void handleVideoMessageEvent(MessageEvent<VideoMessageContent> event) throws IOException {
        // You need to install ffmpeg and ImageMagick.
        handleHeavyContent(
                event.getReplyToken(),
                event.getMessage().getId(),
                responseBody -> {
                    DownloadedContent mp4 = saveContent("mp4", responseBody);
                    DownloadedContent previewImg = createTempFile("jpg");
                    system("convert",
                           mp4.path + "[0]",
                           previewImg.path.toString());
                    reply(((MessageEvent) event).getReplyToken(),
                          new VideoMessage(mp4.getUri(), previewImg.uri));
                });
    }

    @EventMapping
    public void handleUnfollowEvent(UnfollowEvent event) {
        log.info("unfollowed this bot: {}", event);
    }*/
    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String replyToken = event.getReplyToken();
        String userId = event.getSource().getUserId();
        try {
            //TODO : set status milik user id x di tabel status jadi 0
            final String replymsg = database.insertStatusPersonal(userId, "1");
        } catch (Exception ex) {
            Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.replyText(replyToken, "Got followed event");
    }

    @EventMapping
    public void handleJoinEvent(JoinEvent event) {
        Source source = event.getSource();
        String replyToken = event.getReplyToken();
        if (source instanceof GroupSource) {
            String groupId = ((GroupSource) source).getGroupId();
            final String replymsg = database.insertStatusGroup(groupId, "1");
            this.replyText(replyToken, replymsg);
        } else if (source instanceof RoomSource) {
            String roomId = ((RoomSource) source).getRoomId();
            final String replymsg = database.insertStatusMulti(roomId, "1");
            this.replyText(replyToken, replymsg);
        }
        //this.replyText(replyToken, "Joined " + event.getSource());
    }

    /*
    @EventMapping
    public void handlePostbackEvent(PostbackEvent event) {
        String replyToken = event.getReplyToken();
        this.replyText(replyToken, "Got postback data " + event.getPostbackContent().getData() + ", param " + event.getPostbackContent().getParams().toString());
    }

    @EventMapping
    public void handleBeaconEvent(BeaconEvent event) {
        String replyToken = event.getReplyToken();
        this.replyText(replyToken, "Got beacon message " + event.getBeacon().getHwid());
    }

    @EventMapping
    public void handleOtherEvent(Event event) {
        log.info("Received message(Ignored): {}", event);
    }*/
    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
            log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1500) {
            message = message.substring(0, 1500 - 2) + "…";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    /*private void handleHeavyContent(String replyToken, String messageId,
                                    Consumer<MessageContentResponse> messageConsumer) {
        final MessageContentResponse response;
        try {
            response = lineMessagingClient.getMessageContent(messageId)
                                          .get();
        } catch (InterruptedException | ExecutionException e) {
            reply(replyToken, new TextMessage("Cannot get image: " + e.getMessage()));
            throw new RuntimeException(e);
        }
        messageConsumer.accept(response);
    }

    private void handleSticker(String replyToken, StickerMessageContent content) {
        reply(replyToken, new StickerMessage(
                content.getPackageId(), content.getStickerId())
        );
    }*/
    public String firstWord(String input) {

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                return input.substring(0, i);
            }
        }

        return input;
    }

    public String remaindingWord(String input) {

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                return input.substring(i + 1, input.length());
            }
        }

        return input;
    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        Source source = event.getSource();
        String text = content.getText();
        String command = this.firstWord(text).toLowerCase();
        String data = this.remaindingWord(text);
        String key = this.firstWord(data);
        String value = this.remaindingWord(data);

        log.info("Got text message from {}: {}", replyToken, text);
        String status = "";
        if (source instanceof GroupSource) {
            String groupId = ((GroupSource) source).getGroupId();
            status = database.getStatusGroup(groupId);
        } else if (source instanceof RoomSource) {
            String roomId = ((RoomSource) source).getRoomId();
            status = database.getStatusMulti(roomId);
        } else {
            String user = event.getSource().getUserId();
            status = database.getStatusPersonal(user);
        }

        if (status.equals("1")) {
            switch (command) {
                case "save": {
                    if (source instanceof GroupSource) {
                        String groupId = ((GroupSource) source).getGroupId();
                        try {
                            final String replymsg = database.saveGroup(groupId, key, value);
                            this.reply(
                                    replyToken,
                                    new TextMessage(replymsg)
                            );

                        } catch (Exception ex) {
                            Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (source instanceof RoomSource) {
                        String roomId = ((RoomSource) source).getRoomId();
                        try {
                            final String replymsg = database.saveMulti(roomId, key, value);
                            this.reply(
                                    replyToken,
                                    new TextMessage(replymsg)
                            );
                        } catch (Exception ex) {
                            Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String userId = event.getSource().getUserId();
                        lineMessagingClient
                                .getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    if (throwable != null) {
                                        this.replyText(replyToken, throwable.getMessage());
                                        return;
                                    }

                                    try {

                                        final String replymsg = database.savePersonal(userId, key, value);
                                        this.reply(
                                                replyToken,
                                                new TextMessage(replymsg)
                                        );

                                    } catch (Exception ex) {
                                        Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                });

                    }
                    break;
                }
                case "load": {

                    if (source instanceof GroupSource) {
                        try {
                            String groupId = ((GroupSource) source).getGroupId();
                            final String replymsg = database.loadGroup(groupId, key);
                            this.reply(
                                    replyToken,
                                    new TextMessage(replymsg)
                            );
                        } catch (Exception ex) {
                            Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (source instanceof RoomSource) {
                        try {
                            String roomId = ((RoomSource) source).getRoomId();
                            final String replymsg = database.loadMulti(roomId, key);
                            this.reply(
                                    replyToken,
                                    new TextMessage(replymsg)
                            );
                        } catch (Exception ex) {
                            Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String userId = event.getSource().getUserId();
                        if (userId != null) {
                            lineMessagingClient
                                    .getProfile(userId)
                                    .whenComplete((profile, throwable) -> {
                                        if (throwable != null) {
                                            this.replyText(replyToken, throwable.getMessage());
                                            return;
                                        }
                                        try {
                                            final String replymsg = database.load(userId, key);
                                            this.reply(
                                                    replyToken,
                                                    new TextMessage(replymsg)
                                            );
                                        } catch (Exception ex) {
                                            Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    });
                        } else {
                            this.replyText(replyToken, "Bot can't use profile API without user ID");
                        }
                    }
                    break;
                }

                case "boss": {
                    String userId = event.getSource().getUserId();

                    if (userId != null) {
                        lineMessagingClient
                                .getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    if (throwable != null) {
                                        this.replyText(replyToken, throwable.getMessage());
                                        return;
                                    }

                                    boolean success = false;
                                    try {
                                        if (source instanceof GroupSource) {
                                            String groupId = ((GroupSource) source).getGroupId();
                                            success = database.changeStatusGroup(groupId, "0");
                                        } else if (source instanceof RoomSource) {
                                            String roomId = ((RoomSource) source).getRoomId();
                                            success = database.changeStatusMulti(roomId, "0");
                                        } else {
                                            String user = event.getSource().getUserId();
                                            success = database.changeStatusPersonal(user, "0");
                                        }
                                    } catch (Exception ex) {
                                        Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    int index = (int) (Math.random() * (news.length - 1));
//                                    int index = 4;
                                    this.replyText(
                                            replyToken,
                                            news[index]
                                    );
//                                this.reply(
//                                        replyToken,
//                                        Arrays.asList(new TextMessage(
//                                                "ID: " + userId),
//                                                new TextMessage("Active: "
//                                                        + isActive)
//                                        )
//                                );

                                });
                    } else {
                        this.replyText(replyToken, "Bot can't use profile API without user ID");
                    }
                    break;
                }
                case "bye": {
                    //Source source = event.getSource();
                    if (source instanceof GroupSource) {
                        this.replyText(replyToken, "Leaving group");
                        lineMessagingClient.leaveGroup(((GroupSource) source).getGroupId()).get();
                    } else if (source instanceof RoomSource) {
                        this.replyText(replyToken, "Leaving room");
                        lineMessagingClient.leaveRoom(((RoomSource) source).getRoomId()).get();
                    } else {
                        this.replyText(replyToken, "Bot can't leave from 1:1 chat");
                    }
                    break;
                }
                default:
//                    log.info("Returns echo message {}: {}", replyToken, text);
//                    this.replyText(
//                            replyToken,
//                            text
//                    );
                    break;
            }
        } else if (status.equals("0")) {
            boolean success = false;
            if (text.equalsIgnoreCase("noboss")) {
                try {
                    if (source instanceof GroupSource) {
                        String groupId = ((GroupSource) source).getGroupId();
                        success = database.changeStatusGroup(groupId, "1");
                    } else if (source instanceof RoomSource) {
                        String roomId = ((RoomSource) source).getRoomId();
                        success = database.changeStatusMulti(roomId, "1");
                    } else {
                        String user = event.getSource().getUserId();
                        success = database.changeStatusPersonal(user, "1");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(KitchenSinkController.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.reply(
                        replyToken,
                        new TextMessage("Boss mode offline")
                );
            } else {
                String reply = c.sendMessage(text);
                this.reply(
                        replyToken,
                        new TextMessage(reply)
                );
            }
        }

    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).build()
                .toUriString();
    }

    private void system(String... args) {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        try {
            Process start = processBuilder.start();
            int i = start.waitFor();
            log.info("result: {} =>  {}", Arrays.toString(args), i);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            log.info("Interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private static DownloadedContent saveContent(String ext, MessageContentResponse responseBody) {
        log.info("Got content-type: {}", responseBody);

        DownloadedContent tempFile = createTempFile(ext);
        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
            ByteStreams.copy(responseBody.getStream(), outputStream);
            log.info("Saved {}: {}", ext, tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent createTempFile(String ext) {
        String fileName = LocalDateTime.now().toString() + '-' + UUID.randomUUID().toString() + '.' + ext;
        Path tempFile = KitchenSinkApplication.downloadedContentDir.resolve(fileName);
        tempFile.toFile().deleteOnExit();
        return new DownloadedContent(
                tempFile,
                createUri("/downloaded/" + tempFile.getFileName()));
    }

    @Value
    public static class DownloadedContent {

        Path path;
        String uri;
    }

    public KitchenSinkController() throws URISyntaxException, SQLException, IOException {
        this.c = new Cleverbot();
        database = new DatabaseEngine();

    }

    private DatabaseEngine database;
}
