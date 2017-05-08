package zmq.com.tbpatientreferraldoottool.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;

/**
 * Created by zmq162 on 13/10/16.
 */
public class DootConstants {
    public static String EXCEPTION_STRING ="NA";
    public static String DOOT_NAME = "NA";
    public static String DOOT_ID = "NA";
    public static String UPLOAD_PATIENT_STATUS = "FAIZ";
    public static String UPLOAD_LEARNING_ANALYTICS_STATUS = "FAIZ";
    public static String API_KEY = "FAIZ";
    public static String DEVICE_IMEI = "FAIZ";
    public static String LOGIN_KEY = "FAIZ";


    public static final String PREFERENCES = "doot" ;
    public static SharedPreferences sharedPreferences;

    public static final String ENGLISH = "english";
    public static final String HINDI = "hindi";
    public static String LANGUAGE = ENGLISH;

    public static final String LEARNING_ANALYTICS_PREFERENCES = "learning_analytics";
    public static SharedPreferences learningSharedPreferences;
    public static String LEARNING_1 = "TB Understanding";
    public static String LEARNING_2 = "TB Infection";
    public static String LEARNING_3 = "TB Precaution";
    public static String LEARNING_4 = "TB Treatment";
    public static String LEARNING_5 = "TB Care";

    public static String LEARNING_STATUS = "LEARNING_STATUS";

    public static SharedPreferences languageSharedPreference;
    public static final String LANGUAGE_PREFERENCE = "language_preference";

    public static String[] setRegistrationRecord(List<ScreenRegiDtls> regiDtlsListInfo){

        String DOOTID = DOOT_ID;
        String PATIENT_NAME = "";
        String AGE = "";
        String SEX = "";
        String ADDRESS = "";
        String PHONE = "";
        String DISTRICT = "";
        String BLOCK_ID = "";
        String VILLAGE_ID = "";
        String DMCREFFERAL_ID = "";
        String STATE = "";
        String COUNTRY = "";
        String QUESTION_ID = "";
        String PATIENT_SCREENING_RESPONSE = "";
        String PATIENT_SCREENING_DATE_TIME = "";
        String PATIENT_SCREENING_AVERAGE = "";
        String LONGITUDE = "";
        String LATITUDE = "";


        for (int i=0;i<regiDtlsListInfo.size();i++){
            ScreenRegiDtls screenRegiDtls = regiDtlsListInfo.get(i);

            PATIENT_NAME += screenRegiDtls.getPatientName() + ":";
            AGE += screenRegiDtls.getPatientAge() + ":";
            SEX += screenRegiDtls.getPatientSex() + ":";
            ADDRESS += screenRegiDtls.getPatientAddress() + ":";
            PHONE += screenRegiDtls.getPatientPhoneNo() + ":";
            VILLAGE_ID += screenRegiDtls.getPatientVillageId() + ":";
            BLOCK_ID += screenRegiDtls.getPatientBlockId() + ":";
            DMCREFFERAL_ID += screenRegiDtls.getPatientDmcId() + ":";
            DISTRICT += screenRegiDtls.getPatientDistrict() + ":";
            STATE += screenRegiDtls.getPatientState() + ":";
            COUNTRY += screenRegiDtls.getPatientCountry() + ":";
            QUESTION_ID += screenRegiDtls.getPatientQuestionsId() + "#";
            PATIENT_SCREENING_RESPONSE += screenRegiDtls.getPatientScreenResponse() + "#";
            PATIENT_SCREENING_DATE_TIME += screenRegiDtls.getScreenDateTime() + "#";
            PATIENT_SCREENING_AVERAGE += screenRegiDtls.getPatientScreenAverage() + ":";
            LONGITUDE += screenRegiDtls.getLongitude() + ":";
            LATITUDE += screenRegiDtls.getLatitude() + ":";

        }
        if (!(PATIENT_NAME.equals(""))){
            PATIENT_NAME = PATIENT_NAME.substring(0,PATIENT_NAME.length()-1);
            AGE = AGE.substring(0,AGE.length()-1);
            SEX = SEX.substring(0,SEX.length()-1);
            ADDRESS = ADDRESS.substring(0,ADDRESS.length()-1);
            PHONE = PHONE.substring(0,PHONE.length()-1);
            VILLAGE_ID = VILLAGE_ID.substring(0,VILLAGE_ID.length()-1);
            BLOCK_ID = BLOCK_ID.substring(0,BLOCK_ID.length()-1);
            DMCREFFERAL_ID = DMCREFFERAL_ID.substring(0,DMCREFFERAL_ID.length()-1);
            DISTRICT = DISTRICT.substring(0,DISTRICT.length()-1);
            STATE = STATE.substring(0,STATE.length()-1);
            COUNTRY = COUNTRY.substring(0,COUNTRY.length()-1);
            QUESTION_ID = QUESTION_ID.substring(0,QUESTION_ID.length()-1);
            PATIENT_SCREENING_RESPONSE = PATIENT_SCREENING_RESPONSE.substring(0,PATIENT_SCREENING_RESPONSE.length()-1);
            PATIENT_SCREENING_DATE_TIME = PATIENT_SCREENING_DATE_TIME.substring(0,PATIENT_SCREENING_DATE_TIME.length()-1);
            PATIENT_SCREENING_AVERAGE = PATIENT_SCREENING_AVERAGE.substring(0,PATIENT_SCREENING_AVERAGE.length()-1);
            LONGITUDE = LONGITUDE.substring(0,LONGITUDE.length()-1);
            LATITUDE = LATITUDE.substring(0,LATITUDE.length()-1);

            System.out.println("Patient Name>>>>>>>>>>>>"+PATIENT_NAME);
            System.out.println("Patient Answer>>>>>>>>>>>>"+PATIENT_SCREENING_RESPONSE);
            System.out.println("Patient QuestionId>>>>>>>>>>>>"+QUESTION_ID);
            System.out.println("Patient LLonngitude>>>>>>>>>>>>"+LONGITUDE);
            System.out.println("Patient Latitude>>>>>>>>>>>>"+LATITUDE);

        } else {
            System.out.println("Patient Name at else>>>>>>>>>>>>"+PATIENT_NAME);
            System.out.println("Patient Answer at else>>>>>>>>>>>>"+PATIENT_SCREENING_RESPONSE);
        }
        String[] value = {DOOTID,PATIENT_NAME,AGE,SEX,ADDRESS,PHONE,VILLAGE_ID,BLOCK_ID,DMCREFFERAL_ID,DISTRICT,STATE,COUNTRY,
                QUESTION_ID,PATIENT_SCREENING_RESPONSE,PATIENT_SCREENING_DATE_TIME,PATIENT_SCREENING_AVERAGE,LONGITUDE,LATITUDE};
        return value;
    }

    public static String[] setLearningAnalyticsRecord(Activity activity,String[] learningModule){
        String DOOTID = DOOT_ID;
        String CONTENTS = "LC_1"+"#"+"LC_2"+"#"+"LC_3"+"#"+"LC_4"+"#"+"LC_5";

        learningSharedPreferences = activity.getSharedPreferences(LEARNING_ANALYTICS_PREFERENCES,Context.MODE_PRIVATE);
        String[] learningRec = new String[learningModule.length];
        for(int i=0;i<learningModule.length;i++){
            if(learningSharedPreferences.getString(learningModule[i],null) != null) {
                learningRec[i] = learningSharedPreferences.getString(learningModule[i], null).
                        substring(0, learningSharedPreferences.getString(learningModule[i], null).length() - 1);
            } else{
                learningRec[i] = "LeaningNotDone";
            }
        }

        String SUBCONTENTS = learningRec[0]+"#"+learningRec[1]+"#"+
                learningRec[2]+"#"+learningRec[3]+"#"+learningRec[4];
        System.out.println("Sub contents value....."+SUBCONTENTS);

        String[] value = {DOOTID,CONTENTS,SUBCONTENTS};
        return value;
    }

    public static String preMedcine[] = {

            "आज आप एस.एम.एस. भेज कर अपनी दवाई के सेवन की सूचना करेंगे|",
            "आज आप अपनी आवाज़ भेज कर दवाई के सेवन की सूचना करेंगे|",
            "आज आप अपनी वीडियो भेज कर दवाई के सेवन की सूचना करेंगे |",
            "आज आब्जर्वर आपको दवाई खिलने आएंगे|",
            "आज आपको डॉट्स प्रोवाइडर के पास जाकर दवाई खानी होगी|"
    };


    public static String module_1[] = {"टी.बी. से परिचय", "टी.बी. का फैलना", "छिपी हुई टी.बी.", "टी.बी. के मुख्य लक्षण", "सक्रिय टी.बी."};
    public static String module_1_eng[] = {"Tuberculosis", "Transmission of TB", "Latent TB", "Symptoms of TB", "Active TB"};

    public static String module_1_content[] = { "टी.बी. हवा से फैलने वाली छूत कि एक बीमारी हैं| टी.बी. शरीर के किसी भी अंग पर हमला कर सकती हैं, लेकिन फेफड़ों को यह रोग जल्दी पकड़ता है जिसे फेफड़ों का टी.बी. कहते हैं| ",
            "जब टी.बी. से ग्रस्त व्यक्ति छींकता, खांसता, बोलता या थूकता है तो टी.बी. के रोगाणु हवा में फैल जातें है और हवा के माध्यम से किसी भी स्वस्थ व्यक्ति के शरीर में घुसकर उसे टी.बी. से ग्रस्त कर सकते है|",
            "ज़्यादातर टी.बी. के मरीज़ों में रोगाणु निष्क्रिय होता है पर मरीज़ के भीतर ज़िन्दा रहता है, इसे छिपी हुई टी.बी. कहते है|छिपी हुई टी.बी. के मरीज़ में: टी.बी. के कोई लक्षण नहीं दिखाई देते, वह कभी बीमार महसूस नहीं करता, वह दूसरों में टी.बी. नहीं फैला सकता",
            "टी.बी. के मुख्य लक्षण:	तीन हफ्ते से अधिक चलने वाली खांसी, छाती में दर्द होना, भूक ना लगना,	वज़न घटना,	रात को पसीना आना, ठण्ड लगना	बुखार ",
            "जब टी.बी. रोगाणु किसी व्यक्ति के शरीर में सक्रिय हो जाता है और वहां बढ़ता रहता है, तो उसे सक्रिय टी.बी. कहते है| इसके रोगी अन्य किसी भी व्यक्ति को टी.बी. रोगाणु से ग्रस्त करने में सक्षम होते हैं|"
    };
    public static String module_1_content_eng[] = { "Tuberculosis (TB) is an airborne infectious disease. TB germ can attack any part of the body but, lungs are found to be more susceptible to the infection and this type of TB is called Pulmonary TB.  ",
            "When a person infected with TB germ in lungs or throat; coughs, sneezes, speaks or spits, TB germs are propelled in the air and these small amounts of germs can easily infect a healthy person.",
            "Most of the people carrying TB germs, are in inactive state, but remains alive in the body. This condition is called latent TB infection.\n" + "Person carrying latent TB Infection neither feels sick nor shows any TB Symptoms. Also they cannot spread the infection.",
            "Symptoms of TB disease are: Bad cough that lasts 2 weeks or longer, Severe chest pain, Loss of appetite, Unexplained weight loss, Chills, Fever and Sweating at night",
            "When TB germs becomes active in a person’s body and multiplies there causing TB disease then this condition is called Active TB disease. Such people are capable of infecting others with TB germs."
    };

    public static int module_1_sound[] = {R.raw.a1,R.raw.b1,R.raw.c1,R.raw.d1, R.raw.e1};
    public static int module_1_sound_eng[] = {R.raw.a1_eng,R.raw.b1_eng,R.raw.c1_eng,R.raw.d1_eng, R.raw.e1_eng};

    public static int module_1_img[] = {R.drawable.a1,R.drawable.b1,R.drawable.c1,R.drawable.d1, R.drawable.e1};


    public static String module_2[] = {"टी.बी. का फैलना", "टी.बी. रोगी से नजदीकी संपर्क", "कमजोर रोगक्षमता", "विशेष परिस्थितियाँ", "टी.बी. की गलत धारणाएँ","डर, नासमझी और वहम"};
    public static String module_2_eng[] = {"Tuberculosis-Transmission and Risk", "Close contact with TB patient", "Weak immunity", "Special Cases", "Myths around TB","Fear, Misunderstanding and Stigma"};

    public static String module_2_content[] = { "जब टी.बी. से ग्रस्त व्यक्ति छींकता, खांसता, बोलता या थूकता है तो टी.बी. के रोगाणु हवा में फैलकर किसी भी स्वस्थ व्यक्ति के शारीर में घुस जाते हैं और उसे टी.बी. से ग्रस्त कर देते है| टी.बी. के रोगाणु खून के ज़रिये उन अंगों तक फैल जाते हैं जिनमें टी.बी. रोग के होने कि संभावना अधिक होती है जैसे फेफड़े, किडनी, दिमाग एवं रीड की हड्डी|",
            "कोई भी स्वस्थ व्यक्ति यदि टी.बी. रोगी से ग्रसित व्यक्ति के नजदीकी संपर्क में आता है तो उसे भी टी.बी. हो सकता है| टी.बी. संक्रमण की संभावना रोगी के परिवारिक सदस्यों, सह कर्मचारियों और साथ रहने वालों मे ज़्यादा होती है|",
            "यदि एक स्वस्थ व्यक्ति कि रोगक्षमता प्रबल हो तो वह टी.बी. के रोगाणु से सफलतापूर्वक लड़ सकता है| अक्सर देखा गया है कि पर्याप्त खुराक ना लेने वाले व्यक्ति कि रोगक्षमता कमज़ोर होती हैं|",
            "टी.बी. संक्रमण कि संभावना उन लोगों में बढ़ जाती है जो लंबे समय तक शराब व नशीले पदार्थो का सेवन करते हैं और जिनकी एच.आई.वी/एड्स या कैंसर के कारण रोगक्षमता कमज़ोर हो जाती है| एक स्वस्थ्य व्यक्ति कि तुलना में एच.आई.वी संक्रमित व्यक्ति को टी.बी. रोगाणु से संक्रमित होने का खतरा छह गुणा ज़्यादा होता है| ",
            "कुछ गलत धारणाएँ जिनसे टी.बी. नहीं फैलता: -	संक्रमित व्यक्ति के कपड़े पहनने से, खाना खाने और पानी पीने के बर्तनों के इस्तेमाल से,	एक ही शौचालय का इस्तेमाल करने से,	हाथ मिलाने से, चुंबन से",
            "चलिए टी.बी. से संबंधित कुछ वहम मिटाते हैं:- टी.बी. का इलाज सरकारी अस्पतालों में मुफ़्त होता हैं -टी.बी. सिर्फ गरीब व्यक्ति को ही नहीं होता, -टी.बी. हवा के माध्यम से किसी को भी हो सकता है|-	टी.बी. खानदानी बीमारी नहीं है, यह छूत कि बीमारी हैं|-	पूर्ण इलाज से टी.बी. रोगी एक स्वस्थ और सामान्य जीवन जी सकता हैं|",
    };
    public static String module_2_content_eng[] = { "When a person infected with TB germs in lungs or throat coughs, sneezes, speaks or spits, TB germs are propelled in the air and can easily infect a healthy person. TB germs can easily spread to other parts of the body like lungs, kidney, brain and spinal cord, via blood.",
            "Any person can get infected with TB if he/she is in close contact with the TB patient. There is especially a high chance of infection getting spread among the infected person’s family members, roommates, friends or close co-workers.",
            "A person with a healthy immune system can easily fight TB germs while a person with poor diet is more vulnerable to TB as they have a weak immune system.",
            "Long term drug/alcohol users, and people infected with HIV/AIDS, kidney disease, cancer etc. have weak immune system which makes a person vulnerable to TB infection. An HIV positive person is 6 times more likely to develop disease once infected with TB germs.",
            "T.B. do not spread from the following ways:- Sharing Clothes of infected person, drinking and eating in same utensils, using same toilets, shaking hands and kissing.",
            "Let’s clear some misconceptions-TB treatment is free in government health facilities, TB doesn't spread only to the poor, TB is an airborne disease and can infect anyone, TB is not a hereditary disease, it is a communicable infectious disease. If a person takes the full TB treatment, he can lead a normal  and healthy life.",
    };
    public static int module_2_sound[] = {R.raw.a2,R.raw.b2,R.raw.c2, R.raw.e2, R.raw.f2,R.raw.g2};
    public static int module_2_sound_eng[] = {R.raw.a2_eng,R.raw.b2_eng,R.raw.c2_eng, R.raw.e2_eng, R.raw.f2_eng,R.raw.g2_eng};
    public static int module_2_img[] = {R.drawable.a2,R.drawable.b2,R.drawable.c2, R.drawable.e2, R.drawable.f2,R.drawable.g2};

    public static String module_3[] = {"टी.बी. से बचाव और देख-भाल", "टी.बी. होने का कैसे पता लगाएं", "टी.बी. की जांच", "स्वीकार ना करना", "टी.बी. के बारे में सही जानकारी"};
    public static String module_3_eng[] = {"Tuberculosis Prevention & Care", "Diagnosing TB", "Test for TB", "Denial", "Correct information on TB"};

    public static String module_3_content[] = { "टी.बी. से बचाव के तरीके: सफाई के नियमो का पालन करना, घर मे हवा और धूप का आवागमन होना, अन्य परिवारिक सदस्यों का मुह पर मास्क पहनना",
            "यदि आप टी.बी. के लक्षण जैसे कि:	2 हफ़्तो से अधिक खांसी, रात को बुखार बढ़ना, वज़न का घटना, खाना खाने का दिल न करना या जी घबराना, रात को अधिक पसीना आना, अनुभव करें और हाल ही में यदि आप टी.बी. रोगी के संपर्क में आयें है तो सावधानी बरतते हुए स्पुटम कि जांच कराएँ|",
            "टी.बी. में बलगम की जांच सबसे भरोसेमंद जांच मानी जाती है| टी.बी. का इलाज डॉट्स से होता है जिसकी दवा सरकारी केन्द्रों में मुफ्त दी जाती हैं|",
            "टी.बी. का पता चलते ही उसे झुठलाना नहीं चाहिए और इलाज जल्द से जल्द शुरू कर देना चाहिए| यह ज़रूरी है कि आप जांच और इलाज दोनों ही ईमानदारी के करवाएं, इससे आप और आपके परिवार को फायदा होगा|",
            "टी.बी. की जानकारी किसी डॉक्टर या डॉट्स सेंटर से ही लें| ज़्यादा और नई जानकारी आप टी.बी.सी. इंडिया, डब्लू.एच.ओ., सी.दी.सी. जैसे संस्थाओं के वेबसाइट पर भी प्राप्त कर सकते है|"

    };
    public static String module_3_content_eng[] = { "Methods for TB Prevention: Ensure healthy hygiene, Ensure adequate ventilation and plenty of sunlight, Ensure that all other family members cover their mouth",
            "One can get tested for TB if he has any of the following symptoms and thinks that he has been exposed to someone with TB. These are: Bad cough that lasts 2 weeks or longer, fever and sweating at night, loss of appetite and unexplainable weight loss. ",
            "Microscopic sputum examination is the most realistic diagnostic test to know whether a person has TB or not. TB is treated free of cost, through DOTS mechanism in all government health centers. ",
            "Once diagnosed with TB, it is extremely important for the person to go for an immediate treatment. There is no point in denying the fact. It is essential that the person goes for early testing and treatment for the safety of his family members. ",
            "Consult a doctor or your DOTS provider for any information related to TB. You may also visit official websites of TBC India, WHO, CDC, and Stop TB to get latest and authentic information on tuberculosis. "

    };
    public static int module_3_sound[] = {R.raw.a3,R.raw.b3,R.raw.c3,R.raw.d3, R.raw.e3};
    public static int module_3_sound_eng[] = {R.raw.a3_eng,R.raw.b3_eng,R.raw.c3_eng,R.raw.d3_eng, R.raw.e3_eng};
    public static int module_3_img[] = {R.drawable.a3,R.drawable.b3,R.drawable.c3,R.drawable.d3, R.drawable.e3};


    public static String module_4[] = {"टी.बी. का इलाज मुमकिन ", "डॉट्स से परिचय", "डॉट्स लागू करना", "इलाज के दुष्प्रभाव ", "दवाई का असर ना करना","टी.बी. और एच.आई.वी. का संक्रमण"};
    public static String module_4_eng[] = {"TB is curable", "Understanding DOTS", "Implementing DOTS", "Side-effect of treatment", "Drug Resistance","TB and HIV Co-infection"};

    public static String module_4_content[] = { "टी.बी. से बचाव और इलाज दोनों ही संभव है| सरकार के टी.बी. नियंत्रण कार्यक्रम के अंतर्गत स्वास्थय केन्द्रों और सरकारी अस्पतालों में टी.बी. का इलाज मुफ्त में किया जाता है|",
            "डॉट्स उपचार में रोगी को हर दूसरे दिन स्वास्थय कार्यकर्ता को एक जगह जैसे की डॉट्स सेंटर या अस्पताल मे मिलकर उससे दवाई लेनी होती है| कार्यकर्ता प्रत्येक रोगी का चार्ट बनाता है और दी जाने वाली दवाइयों का रिकॉर्ड रखकर रोगी के पूर्ण इलाज को सुनिश्चित करता है|",
            "टी.बी. के रोगाणु शरीर में प्रवेश के बाद दवाइयाँ खाने पर धीरे धीरे मरते है इसलिए 6-9 महीने तक नियमित रूप से दवाई लेनी पढ़ती है| 2-3 महीने 3-4 तरह की दवाइयाँ लेनी होती है और पुनः जाँच के बाद अगले 4-5 महीनों तक कम दवाइयाँ खाने को दी जाती हैं|",
            "हर टी.बी. का इलाज दुष्प्रभाव पैदा नहीं करता लेकिन कभी कभी दवाइयाँ और इलाज कुछ दुष्प्रभाव पैदा कर सकते है| यदि आप त्वचा और आँखों में पीलापन, 3 दिन से अधिक बुखार, पेट दर्द, उँगलियों और अंगूठो में झुनझुनाहट, जोड़ो में दर्द, धुंधला दिखना, कानो में शोरगुल या सुनाई ना देना अनुभव करें तो तुरंत डॉक्टर की सलाह लें|",
            "दवाइयों का असर हो, इसके लिए बहुत ज़रूरी है कि रोगी नियमित रूप से दवाई ले| नियमित रूप से दवाइयाँ ना लेने पर रोगी को एमडीआर हो जाता है जिसमे ज़रूरी दवाइयों का असर होना बंद हो जाता है|",
            "सामान्य लोगो की तुलना एच.आई.वी.संक्रमित लोगो को दूसरे संक्रमण खासतौर से टी.बी. का खतरा अधिक होता है | एच.आई.वी.संक्रमित लोगो में रोगक्षमता कम होने के कारण शरीर टी.बी. से नहीं लड़ पाता और यही बहुत सारे लोगो की मौत का कारण बन जाता है|"
    };
    public static String module_4_content_eng[] = { "TB is both preventable and curable. Government of India under its TB control programme offers free treatment and medicines at various health centers and government hospitals.",
            "According to DOTS strategy, the person infected with TB will meet the healthcare worker every alternate day in a week. A patient wise box of medication is earmarked for each patient, ensuring the availability of drugs for the full course of treatment.",
            "As the TB germs die very slowly, it takes at least 6-9 months to kill the germs and complete the treatment. For 2-3 months patients have to take 3-4 types of drugs and if sputum test results are negative, the patient is prescribed fewer drugs, which has to be taken for 4-5 months.",
            "Occasionally TB medicine and treatment may cause some side-effects, but not everyone develops it. In case, you observe yellowish skin, fever for 3 or more days, abdominal pain, tingling fingers, aching joints, blurred vision, ringing in the ears and hearing loss, consult your doctor immediately.",
            "It is extremely important for a TB patient to take the medicine as prescribed by the doctor. If the patient stops taking the drugs the TB germ becomes resistant to those drugs and can result into MDR TB.",
            "Tuberculosis is a serious threat especially for HIV-infected persons. People infected with HIV are more likely to get sick with other infections because of weak immunity against TB germs, which ultimately results into the death. "
    };
    public static int module_4_sound[] = {R.raw.a4,R.raw.b4,R.raw.c4,R.raw.d4, R.raw.e4,R.raw.f4};
    public static int module_4_sound_eng[] = {R.raw.a4_eng,R.raw.b4_eng,R.raw.c4_eng,R.raw.d4_eng, R.raw.e4_eng,R.raw.f4_eng};
    public static int module_4_img[] = {R.drawable.a4,R.drawable.b4,R.drawable.c4,R.drawable.d4, R.drawable.e4,R.drawable.f4};


    public static String module_5[] = {"टी.बी. रोग से ग्रसित व्यक्ति", "इलाज छोड़-छोड़ कर करना", "टी.बी. का इलाज बीच में छोड़ देना", "टी.बी. रोगी को खुराक की ज़रुरत", "गर्भवती महिला / बच्चे को दूध पिलाना","स्वास्थ्य केंद्र में काम करना", "स्थल बदलने पर दवा जारी रखना"};
    public static String module_5_eng[] = {"Person infected with TB", "Irregular treatment", "Treatment default", "Dietary requirements", "Pregnant/ Lactating mother","Working in health centre", "Transfer cases"};

    public static String module_5_content[] = { "जांच से टी.बी. का पता चलने पर शिघ्र ही इलाज शुरू कराते हुए टी.बी. रोगाणु को फैलने से बचाना चाहिए| कुछ बातों का ध्यान रख कर टी.बी. रोगाणु को फैलने से बचाया जा सकते है, जैसे कि: मुह ढ़कना, खुले में ना थूकना, अकेले सोना, घर में खुली हवा और धूप का आगमन होना, मास्क पहनना,	शीघ्र ही इलाज शुरू करना",
            "यही आप टी.बी. का इलाज छोड़-छोड़ कर करेंगे तो आप इस बीमारी को जड़ से नहीं हटा पाएँगे| ठीक से इलाज ना करवाने से यह बीमारी एमडीआर में परिवर्तित हो सकती है, फ़िर उसका इलाज महंगी दवाइयों से होता हैं| ऐसे इलाज की विधि लगभग दो साल तक चलती हैं|",
            "इलाज अधूरा छोड़ने पर टी.बी. एमडीआर में परिवर्तित हो सकती है, जिसका इलाज सरकार कि मुफ्त दवाइयों से नहीं होता| इस तरह के टी.बी. का इलाज तक़रीबन दो साल तक महंगी दवाइयों से चलता हैं|",
            "डॉट्स दवा के स्ट्रोंग होने के कारण रोगी का वज़न तेज़ी से घटता है, लेकिन उसका सेवन करना अनिवार्य है, इसलिए यह रोगी के लिए बहुत ज़रूरी है कि वह हमेशा पौष्टिक आहार ही खाएँ|",
            "गर्भवती महिला टी.बी. का इलाज करवा सकती है और प्रसव के बाद माँ बच्चे को अपना दूध भी पिला सकती हैं| एक बात का खास ख्याल रखें, आप गर्भवती हैं इसकी जानकारी डॉट्स सेंटर को ज़रूर दें, ताकि ऐसी स्तिथि में वर्जित दवाइयाँ ना दी जाएं| तब रोगी माँ से शिशु में टी.बी. नहीं हो सकता| शिशु के बचाव के लिए अन्य नियमों का पालन करने के साथ जन्म के एक महीना पूरा होने से पहले शिशु को बी.सी.जी. का टीका लगवाना चाहिए|",
            "टी.बी. रोगियों से नजदीकी संपर्क होने की वजह से स्वास्थ्य कर्मचारियों को हमेशा टी.बी. होने का खतरा बना रहता है इसलिए उनका मास्क पहनना और समय समय पर हाथ धोना आवश्यक होता है|",
            "यदि आपके काम करने का स्थल बदल गया है तब भी आप अपनी दवाई जारी रख सकते है| बस अपनी दवाई का कार्ड संभाल के रखें और किसी भी सरकारी केंद्र में उसे दिखा कर अपना इलाज जारी करवा सकते हैं|"
    };
    public static String module_5_content_eng[] = { "Treatment should start as soon as a person is detected TB positive to avoid spreading of infection. Preventive measures includes- Covering of mouth, Avoid spitting openly, Sleeping alone, Ensuring proper ventilation and sunlight, Wearing mask, Early treatment.",
            "If a person is not regular with his treatment, he won’t be able to remove the infection from the roots. This can result into MDR TB, which is treated  with more expensive medicines and the treatment span increases to 2 years.",
            "The treatment of MDR TB is very expensive and is not treated with free medication provided by government. This type of TB treatment is more expensive with the treatment span of nearly 2 years.",
            "Due to heavy DOTS dosage, the patient looses weight very quickly. But it is important to consume the medicine regularly along with proper nutritious diet. ",
            "Pregnant women can also undergo TB treatment and breastfeed her child post delivery. Make sure to inform the DOTS provider so that he doesn’t give prohibited medicines.  As precautionary measure, BCG vaccine is strongly recommended to newborn within one month. ",
            "Health workers are always on risk of getting infected because of close contact with TB patients. Hence, it is important to regularly wash your hands and wear safety mask.",
            "A patient can continue the medicine even when his place of work has changed. In such cases, patient can continue his medicine by showing the treatment card to any nearest government health centre. "
    };
    public static int module_5_sound[] = {R.raw.a5,R.raw.b5,R.raw.c5,R.raw.d5, R.raw.e5,R.raw.g5,R.raw.h5};
    public static int module_5_sound_eng[] = {R.raw.a5_eng,R.raw.b5_eng,R.raw.c5_eng,R.raw.d5_eng, R.raw.e5_eng,R.raw.g5_eng,R.raw.h5_eng};
    public static int module_5_img[] = {R.drawable.a5,R.drawable.b5,R.drawable.c5,R.drawable.d5, R.drawable.e5,R.drawable.g5,R.drawable.h5};

}
