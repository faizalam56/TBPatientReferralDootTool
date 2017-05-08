package zmq.com.tbpatientreferraldoottool.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.activities.DootMenuActivity;
import zmq.com.tbpatientreferraldoottool.activities.MainActivity;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.MySound;

/**
 * Created by zmq162 on 2/11/16.
 */
public class ScreeningFragment extends Fragment implements View.OnClickListener {
    ImageButton backBtn,nextBtn;
    ImageView saveImg,speakerImg,headerImg;
    ViewPager viewPager;
    String[] quesHeader = {"Q:1","Q:2","Q:3","Q:4"};

    String[] quesHeaderHnd = {"प्र:1","प्र:2","प्र:3","प्र:4"};

    String[] ques = {
            "Do you have persistent cough from last two weeks ? ",
            "Do you have complaints of high fever and excessive sweating at night ? ",
            "Do you have swelling problem around your lymph nodes ? ",
            "Is your weight been declining steadily for unexplained  reason ? " };

    String[] quesHnd = {
            " क्या आपको पिछले दो हफ्ते  से लगातार खांसी है ? ",
            " क्या आपको रात में पसीना और तेज़ बुखार की शिकायत है ? ",
            " क्या आपको अपनी छाती और गले पर गांठे नज़र आती है ? ",
            " क्या आपका वजन बिना किसी कारण लगातार घटता जा रहा है? " };
    int[] sondHnd = { R.raw.scr_ques1_hnd, R.raw.scr_ques2_hnd,R.raw.scr_ques3_hnd,R.raw.scr_ques4_hnd};
    int[] sondEng = { R.raw.scr_ques1_eng,R.raw.scr_ques2_eng,R.raw.scr_ques3_eng,R.raw.scr_ques4_eng };
    int[] screeningQuesImg = { R.drawable.quest1,R.drawable.quest2,R.drawable.quest3,R.drawable.quest4};

    int[] response = new int[ques.length];
    String[] ansValue = {"","","",""};
    boolean check_state = false;
    RadioButton checkYes,checkNo;
    RadioGroup radioGroup;
    ScreeningFragmentCommunicator communicator;
    String screen_date_time;
    boolean yes,no,save;
    int index;

    String message = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please choose an option":"कृपया एक विकल्प चुनिए। ";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screening_fragment,container,false);
        return view;
    }

    public void setScreeningFragmentCommunicator(ScreeningFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        backBtn = (ImageButton) getActivity().findViewById(R.id.back_btn);
        nextBtn = (ImageButton) getActivity().findViewById(R.id.next_btn);
        speakerImg = (ImageView) getActivity().findViewById(R.id.speaker);
        saveImg = (ImageView) getActivity().findViewById(R.id.save);
//        headerImg = (ImageView) getActivity().findViewById(R.id.header_image);

//        headerImg.setImageResource(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?R.drawable.screening:R.drawable.screening_hindi);

        viewPager.setAdapter(new MyAdapter());

        MySound.playSound(getContext(), DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? sondEng[viewPager.getCurrentItem()] : sondHnd[viewPager.getCurrentItem()]);
        speakerImg.setImageResource(R.drawable.speaker_mute);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int newPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                System.out.println("$$ onPageScrolled " + position);
                if (viewPager.getCurrentItem() != 3) {
                    saveImg.setVisibility(View.INVISIBLE);
                    nextBtn.setVisibility(View.VISIBLE);
                } else {
                    saveImg.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.INVISIBLE);
                }

                if (viewPager.getCurrentItem() != 0) {
                    backBtn.setVisibility(View.VISIBLE);
                    if (MySound.mediaPlayer != null && MySound.mediaPlayer.isPlaying()) {
                        MySound.stopSound();
                    }
                } else {
                    backBtn.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("$$ onPageSelected " + position);
                if (yes || no) {
                    yes = false;
                    no = false;
                    viewPager.setCurrentItem(position);
                    newPosition = position;

                    if (response[position] == 1) {
                        yes = true;
                    } else if (response[position] == 2) {
                        no = true;
                    }
                } else {
                    viewPager.setCurrentItem(newPosition);
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
                radioButtonState(viewPager.getCurrentItem());

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("$$ onPageScrollStateChanged " + viewPager.getCurrentItem());
                if (MySound.mediaPlayer != null && MySound.mediaPlayer.isPlaying()) {
                    MySound.stopSound();
                }
                MySound.playSound(getContext(), DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? sondEng[viewPager.getCurrentItem()] : sondHnd[viewPager.getCurrentItem()]);
                speakerImg.setImageResource(R.drawable.speaker_mute);
            }
        });

        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        speakerImg.setOnClickListener(this);
        saveImg.setOnClickListener(this);

        screen_date_time = getScreenDateTime();

    }



    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                System.out.println("back value..." + index);
                if(yes || no) {
                    viewPager.setCurrentItem(getItem(-1), true);
                    if (MySound.mediaPlayer!=null && MySound.mediaPlayer.isPlaying()) {
                        MySound.stopSound();

                    }
                    yes = false;
                    no = false;
                    if(response[viewPager.getCurrentItem()]==1){
                        yes = true;
                    }else if(response[viewPager.getCurrentItem()]==2){
                        no = true;
                    }

                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.next_btn:
                System.out.println("Next value..." + index);
                if(yes || no) {
                    viewPager.setCurrentItem(getItem(+1), true);
                    if (MySound.mediaPlayer!=null && MySound.mediaPlayer.isPlaying()) {
                        MySound.stopSound();

                    }
                    yes = false;
                    no = false;
                    if(response[viewPager.getCurrentItem()]==1){
                        yes = true;
                    }else if(response[viewPager.getCurrentItem()]==2){
                        no = true;
                    }

                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.speaker:

                    if (MySound.mediaPlayer!=null && MySound.mediaPlayer.isPlaying()) {
                        MySound.stopSound();
                        speakerImg.setImageResource(R.drawable.speaker_on);
                    } else {
                        MySound.playSound(getContext(), DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? sondEng[viewPager.getCurrentItem()] : sondHnd[viewPager.getCurrentItem()]);
                        speakerImg.setImageResource(R.drawable.speaker_mute);
                    }

                break;
            case R.id.save:
                if(yes || no) {
                    System.out.println("save button is pressing.....");
                    String answer = ansValue[0] + ":" + ansValue[1] + ":" + ansValue[2] + ":" + ansValue[3];
                    System.out.println("Answer....."+answer);
                    int screenAverage = getScreeningResult(ansValue);
                    screeningConfirmationMessage(answer,screenAverage);
                    if (MySound.mediaPlayer!=null && MySound.mediaPlayer.isPlaying()) {
                        MySound.stopSound();
                    }
                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (MySound.mediaPlayer!=null && MySound.mediaPlayer.isPlaying()) {
            MySound.stopSound();

        }
    }
    private void screeningConfirmationMessage(String answer,int screenAverage){
        final String answers = answer;
        final int scrAvg = screenAverage;
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "CONFIRMATION" : "पुष्टीकरण");
        dialog.setIcon(R.drawable.icon);
        dialog.setMessage(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "Screening complete. Please register for referral." : "स्क्रीनिंग पूरी हुई। कृपया रेफेरल के लिए रजिस्टर करें।");
        dialog.setPositiveButton(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ?"Register":"रजिस्टर", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                communicator.saveScreeningResult(answers, screen_date_time,scrAvg);
            }
        });
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeActivity = new Intent(getActivity(),DootMenuActivity.class);
                homeActivity.addCategory(Intent.CATEGORY_HOME);
                homeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                homeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ((Activity)getActivity()).finish();
                startActivity(homeActivity);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }



    class MyAdapter extends PagerAdapter{

        LayoutInflater layoutInflater;
        ImageView quesImg;

        TextView quesText,quesHeaderText;


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView;
            System.out.println("position..."+position+"....Index..."+index);
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.view_screening_item,container,false);

            quesImg = (ImageView) itemView.findViewById(R.id.questions_image);
            quesText = (TextView) itemView.findViewById(R.id.questions);
            quesHeaderText = (TextView) itemView.findViewById(R.id.questions_header);

            quesText.setText(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? ques[position] : quesHnd[position]);
            quesHeaderText.setText(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? quesHeader[position] : quesHeaderHnd[position]);
            quesImg.setImageResource(screeningQuesImg[position]);

            radioGroup = (RadioGroup) itemView.findViewById(R.id.radio_group);
            checkNo = (RadioButton) itemView.findViewById(R.id.check_no);
            checkYes = (RadioButton) itemView.findViewById(R.id.check_yes);
            checkYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(" check radio yes");
                    ansValue[viewPager.getCurrentItem()] = "Yes";
                    response[viewPager.getCurrentItem()] = 1;
                    yes = true;
                    no = false;


                }
            });
            checkNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(" check radio No");
                    ansValue[viewPager.getCurrentItem()] = "No";
                    response[viewPager.getCurrentItem()] = 2;
                    yes = false;
                    no = true;

                }
            });


//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    if(checkedId==R.id.check_yes){
//                        System.out.println(" check radio yes");
//                        ansValue[position] = "Yes";
//                        response[position] = 1;
//                        yes = true;
//                        no = false;
//                    }else if(checkedId==R.id.check_no){
//                        System.out.println(" check radio No");
//                        ansValue[position] = "No";
//                        response[position] = 2;
//                        yes = false;
//                        no = true;
//                    }
//                }
//            });

            ((ViewPager) container).addView(itemView,0);
            return itemView;
        }
        @Override
        public int getCount() {
            return ques.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((FrameLayout) object);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((FrameLayout) object);

        }
    }

    private void radioButtonState(int position){

        if(ansValue[position].equals("No")){
            System.out.println("If "+ansValue[position]);
            checkNo.setChecked(true);
        }
        else if(ansValue[position].equals("Yes")){
            System.out.println("else If "+ansValue[position]);
            checkYes.setChecked(true);
        }
        else{
            checkNo.setChecked(false);
            checkYes.setChecked(false);
        }
        System.out.println("radioButtonState...."+position+"Answer.."+ansValue[position]);
    }

    private String getScreenDateTime(){
        String screenDateTime;
        Calendar c = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        screenDateTime = simpleDateFormat.format(c.getTime());
        return screenDateTime;
    }

    private int getScreeningResult(String[] ansValues) {
        int count = 0;
        for (int i = 0; i < ansValues.length; i++) {
            if (ansValues[i].equals("Yes")) {
                count++;
            }
        }
        if (count == 0) {
            return 0;
        } else if (count == 1) {
            return 25;
        } else if (count == 2) {
            return 50;
        } else if (count == 3) {
            return 75;
        } else if (count == 4) {
            return 100;
        }
        return count;
    }

    public interface ScreeningFragmentCommunicator{
        void saveScreeningResult(String ans,String scrDateTime,int screenAvg);
    }

}
