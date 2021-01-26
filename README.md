### Conversant CMP Readme

##### Introduction

###### The Conversant CMP Widget allows publishers to do two essential functions:

1.    Determine whether consent needs to be gathered based upon the users location.
2.    Gather consent once it has been determined it is required. Consent is gathered using a widget which allows a user to accept data gathering on a company and purpose basis.  Users can also accept or deny all consent.


##### Installation
###### Add the compiled AAR file (the library must be already built)
1. Click **File > New > Import Module**.
2. Click **Import .JAR/.AAR Package** then click **Next**.
3. Enter the location of the compiled AAR file of cmp's widget then
   click **Finish**.
4. Open the app module's build.gradle file and add a new line to the
dependencies block as shown in the following snippet:

```
dependencies {
    implementation project(":cmp")
}
```

5. Click **Sync Project with Gradle Files**.

##### Using the library module.


Generally speaking you should gather consent as soon as you first launch
your app. However, you can do it at any point you prefer. It is
essential that you initialize the app as soon as possible. Remember, any
third party frameworks, such as advertising or analytics will NOT BE
ABLE TO OPERATE until you have gathered consent, so it is essential that
you determine your consent requirements and gather consent before you
initialize your other frameworks. However, most high quality frameworks
should be checking for consent and should also be watching the consent
values for changes, so it may be alright to initialize those frameworks
and then gather consent. Read documentation for each framework and act
accordingly.

##### How to use?
###### 1.Import the library module where you would like to initialize the app.

###### Kotlin.
```
import com.conversantmedia.gdprcmp.ConversantCmp
```
###### 2.Create JSON configuration either manually or load from an URL and
initialize the ConversantCMP widget within the Activity. If you are
unsure of initialization parameters for these objects, check the
documentation for each function (option click on the function).

To load the **static** config data

* Initializing the ConversantCmp class first if your using inside the  fragment class check for context before initilazation and pass the static JSON config data as parameter to the cmp's method **setConfig**.

###### Kotlin.
```kotlin
 private fun initConversantCmp() {
        cmp = context?.let { ConversantCmp(it) }
        cmp?.setConfig(getStaticConfig())
    }

private fun getStaticConfig(): String {
        return "{\"countryCode\":\"US\"\n" +
                ",\"gdprAppliesGlobally\":true,\n" +
                "\"policyUrl\":\"http://www.adtech123.com/privacy/\"\n" +
                ",\"version\":\"1\"\n" +
                ",\"id\":1\n" +
                "}"
    }
```

###### Java.
```
private void initConversantCmp(){
        try {
            cmp = new ConversantCmp(this);
            cmp.setConfig(getStaticConfig());
        } catch (CnvrCmpException e) {
           e.printStackTrace();
        }
    }
    
private String getStaticConfig() {
        return "{\"countryCode\":\"US\"\n" +
                ",\"gdprAppliesGlobally\":true,\n" +
                "\"policyUrl\":\"http://www.adtech123.com/privacy/\"\n" +
                ",\"version\":\"1\"\n" +
                ",\"id\":1\n" +
                "}";
    }
```

* Please note, the gdprAppliesGlobally will be required to be set to
  true for testing the CMP widget in case the publisher is not located
  within a region where GDPR regulations apply.
* This configuration parameter is used to signify that the CMP should
  show for every user regardless of GEO location/IP Address. This would
  also be the case if the publisher is located within the jurisdiction
  of the EU and would like to apply the GDPR regulations to all of its
  users.

###### 3. To present the CMP widget popup use the following

###### Kotlin.
```kotlin
 button.setOnClickListener {
            activity?.let { it1 -> cmp?.presentCMPWidget(it1, requestCode = 100) }
        }
```

###### Java.
```
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    conversantCmp.presentCMPWidget(YourActivity.this,   100);
            }
        });
```
###### 4. To allow the user to modify consent use the following

###### Kotlin.
```kotlin
 button.setOnClickListener {
            activity?.let { it1 ->
                cmp?.modifyConsent(it1, requestCode = 100)
            }
        }
```

###### java.
```
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    conversantCmp.modifyConsent(YourActivity.this,   100);
            }
        });
```

###### 5. To check if GDPR consent is required use the following. Result will be an optional NULL in case of timeout depending upon the language used.

###### kotlin.
```kotlin
cmp?.checkGDPRIsRequired(activity, object : OnCompletion {
            override fun onComplete(isRequire: Boolean?) {
                if (isRequire != null) {
                //GDPR status if it is required true else return false
                }
            }
        })
```

###### java.
```
cmp.checkGDPRIsRequired(this, new ConversantCmp.OnCompletion() {
            @Override
            public void onComplete(@Nullable Boolean isRequire) {
                if (isRequire != null){
                   //GDPR status if it is required true else return false
                }
            }
        });
```

* Please note that the GDPR check is already called internally for both
  present and modify consent APIs so not required to be called
  additionally for those two functions.



###### 6. To delete the stored consents, just delete the required keys from the
shared preference as per requirement. Here we are deleting two keys as
an example.

###### kotlin.
```kotlin

revoke.setOnClickListener {
            prefs?.edit()?.remove("IABTCF_TCString")?.apply()
            prefs?.edit()?.remove("CNVR_PersistentData")?.apply()
        }
```

###### java.
```
 revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     sharedPreferences.edit().remove("IABTCF_TCString").apply();
                     sharedPreferences.edit().remove("CNVR_PersistentData").apply();
            }
        });
```