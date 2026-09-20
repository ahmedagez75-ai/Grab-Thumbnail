package com.example.ui

object Strings {
    fun appName(isAr: Boolean) = "ThumbGrab"
    fun appSubtitle(isAr: Boolean) = if (isAr) "تحميل الصور المصغرة من يوتيوب" else "YouTube Thumbnail Downloader"

    // Nav
    fun navHome(isAr: Boolean) = if (isAr) "الرئيسية" else "Home"
    fun navHowItWorks(isAr: Boolean) = if (isAr) "كيفية الاستخدام" else "How It Works"
    fun navFaq(isAr: Boolean) = if (isAr) "الأسئلة الشائعة" else "FAQ"

    // Hero
    fun heroTitle(isAr: Boolean) = if (isAr) "حمّل الصورة المصغرة من أي فيديو يوتيوب" else "Download YouTube Thumbnails in Full HD"
    fun heroSubtitle(isAr: Boolean) = if (isAr) "استخرج صورة الفيديو المصغرة بجودة عالية خلال ثوانٍ." else "Extract high-resolution video thumbnails in seconds."
    fun heroSupporting(isAr: Boolean) = if (isAr)
        "ألصق رابط فيديو يوتيوب، وسنقوم باستخراج الصورة المصغرة وعنوان الفيديو تلقائيًا لتتمكن من معاينتها وتحميلها بسهولة."
    else
        "Paste any YouTube video link, and we'll automatically detect the thumbnail and title for seamless preview and instant download."

    // Downloader Card
    fun cardTitle(isAr: Boolean) = if (isAr) "حمّل الصورة المصغرة" else "Extract Thumbnail"
    fun inputPlaceholder(isAr: Boolean) = if (isAr) "ألصق رابط فيديو يوتيوب هنا..." else "Paste YouTube video link here..."
    fun pasteBtn(isAr: Boolean) = if (isAr) "لصق" else "Paste"
    fun clearBtn(isAr: Boolean) = if (isAr) "مسح" else "Clear"
    fun extractBtn(isAr: Boolean) = if (isAr) "استخراج الصورة" else "Extract Thumbnail"
    fun processing(isAr: Boolean) = if (isAr) "جاري الاستخراج..." else "Extracting..."
    fun sampleLinkBtn(isAr: Boolean) = if (isAr) "جرّب رابطًا تجريبيًا" else "Try Sample Link"

    // Result Card
    fun successBadge(isAr: Boolean) = if (isAr) "تم استخراج الصورة بنجاح ✓" else "Thumbnail extracted successfully ✓"
    fun videoTitleLabel(isAr: Boolean) = if (isAr) "عنوان الفيديو" else "Video Title"
    fun videoIdLabel(isAr: Boolean) = if (isAr) "معرّف الفيديو" else "Video ID"
    fun highestQualityLabel(isAr: Boolean) = if (isAr) "أعلى جودة متاحة" else "Highest Available Quality"
    fun downloadMainBtn(isAr: Boolean) = if (isAr) "تحميل الصورة المصغرة" else "Download Thumbnail"
    fun openOriginalBtn(isAr: Boolean) = if (isAr) "فتح الصورة الأصلية" else "Open Original"
    fun copyUrlBtn(isAr: Boolean) = if (isAr) "نسخ رابط الصورة" else "Copy Image Link"
    fun availableResolutions(isAr: Boolean) = if (isAr) "اختر الجودة المطلوبة:" else "Select Quality:"
    fun previewHint(isAr: Boolean) = if (isAr) "اضغط على الصورة للمعاينة بملء الشاشة" else "Tap image for fullscreen lightbox"
    fun closeLightbox(isAr: Boolean) = if (isAr) "إغلاق" else "Close"

    // How It Works
    fun howItWorksTitle(isAr: Boolean) = if (isAr) "كيف يعمل ThumbGrab؟" else "How ThumbGrab Works"
    fun step1Num(isAr: Boolean) = "01"
    fun step1Title(isAr: Boolean) = if (isAr) "ألصق الرابط" else "Paste the Link"
    fun step1Desc(isAr: Boolean) = if (isAr) "ألصق رابط فيديو يوتيوب في مربع البحث." else "Paste the YouTube video link into the search box."

    fun step2Num(isAr: Boolean) = "02"
    fun step2Title(isAr: Boolean) = if (isAr) "استخراج الصورة" else "Extract Thumbnail"
    fun step2Desc(isAr: Boolean) = if (isAr) "نستخرج الصورة المصغرة وعنوان الفيديو تلقائيًا." else "We automatically retrieve the thumbnail and video metadata."

    fun step3Num(isAr: Boolean) = "03"
    fun step3Title(isAr: Boolean) = if (isAr) "حمّل بجودة عالية" else "Download in HD"
    fun step3Desc(isAr: Boolean) = if (isAr) "عاين الصورة وحمّلها بسهولة بنقرة واحدة." else "Preview the thumbnail and download it with a single tap."

    // Features
    fun featuresTitle(isAr: Boolean) = if (isAr) "لماذا تستخدم ThumbGrab؟" else "Why Choose ThumbGrab?"

    fun feature1Title(isAr: Boolean) = if (isAr) "⚡ سريع" else "⚡ Lightning Fast"
    fun feature1Desc(isAr: Boolean) = if (isAr) "استخرج الصورة المصغرة خلال ثوانٍ." else "Extract and preview thumbnails in seconds."

    fun feature2Title(isAr: Boolean) = if (isAr) "🖼️ جودة عالية" else "🖼️ Highest Quality"
    fun feature2Desc(isAr: Boolean) = if (isAr) "احصل على أعلى دقة متاحة للصورة حتى 1080p." else "Retrieve max resolution up to 1080p / 720p HD."

    fun feature3Title(isAr: Boolean) = if (isAr) "📱 متجاوب بالكامل" else "📱 All Devices"
    fun feature3Desc(isAr: Boolean) = if (isAr) "واجهة سلسة متوافقة تمامًا مع الهواتف والأجهزة اللوحية." else "Optimized for mobile, tablet, and desktop screens."

    fun feature4Title(isAr: Boolean) = if (isAr) "🎯 بسيط ومباشر" else "🎯 Clean & Simple"
    fun feature4Desc(isAr: Boolean) = if (isAr) "بدون خطوات معقدة أو إعلانات مزعجة." else "No complicated steps, straightforward and clutter-free."

    fun feature5Title(isAr: Boolean) = if (isAr) "🔒 خصوصية تامة" else "🔒 Privacy-First"
    fun feature5Desc(isAr: Boolean) = if (isAr) "لا تطلب الأداة إنشاء حساب لتحميل الصور." else "No user account or personal login needed to download."

    // Trust Badges
    fun trustNoSignup(isAr: Boolean) = if (isAr) "بدون تسجيل" else "No Sign-up"
    fun trustCleanUi(isAr: Boolean) = if (isAr) "واجهة بسيطة" else "Minimal UI"
    fun trustDirectDownload(isAr: Boolean) = if (isAr) "تحميل مباشر" else "Direct Download"
    fun trustMobileReady(isAr: Boolean) = if (isAr) "متوافق مع الهاتف" else "Mobile Ready"

    // FAQ
    fun faqTitle(isAr: Boolean) = if (isAr) "الأسئلة الشائعة" else "Frequently Asked Questions"

    val faqs = listOf(
        Pair(
            { isAr: Boolean -> if (isAr) "هل الأداة مجانية؟" else "Is ThumbGrab free to use?" },
            { isAr: Boolean -> if (isAr) "نعم، أداة ThumbGrab مجانية تمامًا لجميع المستخدمين ولا تتطلب أي رسوم أو اشتراكات." else "Yes, ThumbGrab is completely free with no subscriptions or hidden charges." }
        ),
        Pair(
            { isAr: Boolean -> if (isAr) "هل يمكنني تحميل الصورة بجودة عالية؟" else "Can I download in maximum high definition?" },
            { isAr: Boolean -> if (isAr) "نعم، تقوم الأداة بالتحقق الذكي من أعلى دقة رفعها صانع المحتوى (مثل 1280x720 HD أو 1080p) وتوفرها للتحميل المباشر." else "Yes, the tool detects the highest resolution uploaded by the creator (up to 1280x720 HD) and provides direct downloads." }
        ),
        Pair(
            { isAr: Boolean -> if (isAr) "هل تعمل الأداة مع روابط YouTube Shorts؟" else "Does it support YouTube Shorts links?" },
            { isAr: Boolean -> if (isAr) "نعم، تدعم الأداة جميع صيغ روابط يوتيوب بما فيها Shorts وWatch وروابط youtu.be والبث المباشر." else "Yes, it supports all YouTube link formats including Shorts, standard watch URLs, youtu.be, and Live streams." }
        ),
        Pair(
            { isAr: Boolean -> if (isAr) "هل أحتاج إلى تثبيت برنامج إضافي؟" else "Do I need to install any extra software?" },
            { isAr: Boolean -> if (isAr) "لا تحتاج إلى تثبيت أي برامج أو إضافات؛ تعمل الأداة مباشرة وبنقرة واحدة." else "No extra software is required; everything works natively with a single click." }
        ),
        Pair(
            { isAr: Boolean -> if (isAr) "هل تعمل على الهاتف المحمول؟" else "Does it work seamlessly on mobile?" },
            { isAr: Boolean -> if (isAr) "نعم، صُممت الواجهة وفق مبدأ الهاتف أولاً لتوفير تجربة سريعة وخفيفة على جميع الأجهزة الذكية." else "Yes, it is designed mobile-first for blazing-fast performance on all smartphones." }
        ),
        Pair(
            { isAr: Boolean -> if (isAr) "لماذا تختلف جودة الصورة من فيديو لآخر؟" else "Why does thumbnail quality vary between videos?" },
            { isAr: Boolean -> if (isAr) "تعتمد جودة الصورة على الدقة الأصلية التي رفعها صانع المحتوى على يوتيوب؛ بعض الفيديوهات القديمة تتضمن جودة متوسطة فقط." else "Thumbnails reflect the maximum resolution originally uploaded by the video creator; older videos may only have standard definition." }
        )
    )

    // Footer
    fun footerDesc(isAr: Boolean) = if (isAr)
        "أداة بسيطة وسريعة لاستخراج الصور المصغرة من فيديوهات يوتيوب بأعلى جودة ممكنة."
    else
        "A fast, premium tool to extract and download YouTube video thumbnails in the highest quality available."
    fun footerPrivacy(isAr: Boolean) = if (isAr) "سياسة الخصوصية" else "Privacy Policy"
    fun footerTerms(isAr: Boolean) = if (isAr) "شروط الاستخدام" else "Terms of Use"
    fun footerCopyright(isAr: Boolean) = if (isAr) "© 2026 ThumbGrab. جميع الحقوق محفوظة." else "© 2026 ThumbGrab. All rights reserved."

    // Errors
    fun errInvalidUrl(isAr: Boolean) = if (isAr)
        "يبدو أن الرابط غير صالح. تأكد من إدخال رابط فيديو يوتيوب صحيح."
    else
        "The link appears invalid. Please enter a valid YouTube video URL."

    fun errUnsupportedUrl(isAr: Boolean) = if (isAr)
        "هذا الرابط غير مدعوم حاليًا. استخدم رابطًا مباشرًا لفيديو يوتيوب."
    else
        "This link is unsupported. Please enter a direct YouTube link."

    fun errNoThumbnail(isAr: Boolean) = if (isAr)
        "تعذر العثور على صورة مصغرة لهذا الفيديو."
    else
        "Could not find a thumbnail for this video."

    fun errNetwork(isAr: Boolean) = if (isAr)
        "حدث خطأ أثناء استخراج الصورة. حاول مرة أخرى."
    else
        "An error occurred while extracting the thumbnail. Please try again."

    fun errEmptyInput(isAr: Boolean) = if (isAr)
        "ألصق رابط فيديو يوتيوب أولاً للبدء."
    else
        "Please paste a YouTube video link first."

    // Feedbacks
    fun feedbackCopied(isAr: Boolean) = if (isAr) "تم نسخ الرابط ✓" else "Link copied to clipboard ✓"
    fun feedbackSaved(isAr: Boolean) = if (isAr) "تم حفظ الصورة في المعرض ✓" else "Thumbnail saved to gallery ✓"
    fun feedbackSaveFailed(isAr: Boolean) = if (isAr) "تعذر حفظ الصورة. حاول مجددًا." else "Failed to save thumbnail."
}
