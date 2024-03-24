## Import Lib

~~~
// on build.gradle
        maven {
            url 'https://artifactory.apero.vn/artifactory/gradle-release/'
            credentials {
                username "reporeader"
                password "apero@123"
            }
        }
        
// on build.gradle(app)
    implementation 'apero:language.first.open:1.1.0@aar'
~~~

## Set language

~~~
// on Splash class
    LanguageFirstOpen languageFirstOpen = new LanguageFirstOpen(this);

// config value class language first open
            List<Language> list = new ArrayList<>();
            list.add(new Language("en", getString(R.string.language_english), R.drawable.ic_language_en, false));
            list.add(new Language("hi", getString(R.string.language_hindi), R.drawable.ic_language_hi, false));
            list.add(new Language("ja", getString(R.string.language_japan), R.drawable.ic_language_ja, false));
            ....

            LanguageFirstOpen.Companion.setLanguagesLimitItem(list);
            LanguageFirstOpen.Companion.setSizeLayoutLoadingAdNative(300f);// use dp
            LanguageFirstOpen.Companion.setIcCheckedLanguage(R.drawable.ic_language_checked);
            LanguageFirstOpen.Companion.setBgToolbar(R.drawable.shape_bg_toolbar_language);
            LanguageFirstOpen.Companion.setTitleLanguage(getString(R.string.language));
            LanguageFirstOpen.Companion.setShowNative(!AppPurchase.getInstance().isPurchased());
            LanguageFirstOpen.Companion.setCountLimit(5);
            LanguageFirstOpen.Companion.setScreenMode(LIGHT_MODE);

// call start language first open
            languageFirstOpen.startLanguageFirstOpen(language -> {
                // code language select result
            });
            
// load language first open
                    AperoAd.getInstance()
                .loadNativeAdResultCallback(this, "ID_NATIVE_AD",com.ltl.apero.languageopen.R.layout.view_native_ads_language_first, object : AperoAdCallback(){
                    override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                        super.onNativeAdLoaded(nativeAd)
                        nativeAdsLanguage.postValue(nativeAd)
                    }
                    override fun onAdFailedToLoad(adError: ApAdError?) {
                        super.onAdFailedToLoad(adError)
                        nativeAdsLanguage.postValue(ApNativeAd(StatusAd.AD_LOAD_FAIL))
                    }
                }
// load language first open select
                    AperoAd.getInstance()
                .loadNativeAdResultCallback(this, "ID_NATIVE_AD",com.ltl.apero.languageopen.R.layout.custom_native_ads_language_first, object : AperoAdCallback(){
                    override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                        super.onNativeAdLoaded(nativeAd)
                        nativeAdsLanguageSelect.postValue(nativeAd)
                    }
                    override fun onAdFailedToLoad(adError: ApAdError?) {
                        super.onAdFailedToLoad(adError)
                        nativeAdsLanguageSelect.postValue(ApNativeAd(StatusAd.AD_LOAD_FAIL))
                    }
                })
~~~
