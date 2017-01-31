package com.bnet.tnet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.services.utils.ProvidableUtils;
import com.bnet.tnet.R;
import com.bnet.tnet.model.Filter;
import com.bnet.tnet.model.FilterDecorator;
import com.bnet.tnet.view.ShortTravelDetails;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AgencyDetails extends android.app.Activity {
    private Business agency = new Business();

    private Toolbar toolbar;

    private TextView agencyStreet;
    private TextView agencyPhone;
    private TextView agencyEmail;
    private TextView agencyLink;
    private TextView businessName;


    private ShortTravelDetails shortTravelDetails;

    private RecyclerView recyclerView;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agency_details);

        retrieveBusinessFromIntent(getIntent());

        findViews();
        setUpDetailsButtons();
        setUpToolBar();
        bindAgencyDetails();

        setupBottomSheetBehavior();
        setupTravelsAdapter();



    }

    private void setUpToolBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpDetailsButtons() {
        (findViewById(R.id.openMap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri geoLocation= null;
                try {
                    geoLocation = Uri.parse("geo:0,0?q="+URLEncoder.encode(agencyStreet.getText().toString(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(geoLocation);
                if(intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
            }
        });
        (findViewById(R.id.callPhone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+agencyPhone.getText().toString()));
                if(intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
            }
        });
        (findViewById(R.id.sendEmail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                String[] address=new String[1];
                address[0]=agencyEmail.getText().toString();
                intent.putExtra(Intent.EXTRA_EMAIL,address );
                intent.putExtra(Intent.EXTRA_SUBJECT, "From TNet App: ");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        (findViewById(R.id.openLink)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AgencyDetails.this);
                builder.setMessage("Would you like to open the link inside the app?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri webpage = Uri.parse(addHttpIfNeeded(agencyLink.getText().toString()));
                                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            }
                        });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                        intent.putExtra("LINK", agencyLink.getText());
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        });
    }
    private String addHttpIfNeeded(String url)
    {
        if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
            url = "www."+url;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://"+url;
        }
        return url;
    }
    private void retrieveBusinessFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.BUSINESSES_URI_PATH);
        agency = ProvidableUtils.bundleConvert(Business.class, bundle);
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        shortTravelDetails = (ShortTravelDetails) findViewById(R.id.short_travel_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar_back);

        agencyStreet = (TextView) findViewById(R.id.agencyStreet);
        agencyPhone = (TextView) findViewById(R.id.agencyPhone);
        agencyEmail = (TextView) findViewById(R.id.agencyEmail);
        agencyLink = (TextView) findViewById(R.id.agencyLink);
        businessName = (TextView) findViewById(R.id.businessName);

    }

    private void bindAgencyDetails() {
        toolbar.setTitle(agency.getName());
        agencyStreet.setText(agency.getAddress().toString());
        agencyPhone.setText(agency.getPhone());
        agencyEmail.setText(agency.getEmail());
        agencyLink.setText(agency.getLinkToWebsite());
        businessName.setText(agency.getName());
    }
    private void setupBottomSheetBehavior() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void setupTravelsAdapter() {
        FilterDecorator<Activity> myTravels = getMyTravels();

        TravelsAdapter adapter = new TravelsAdapter(myTravels);

        adapter.setOnItemClickListener(new TravelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Activity travel) {
                bindTravelDetails(travel);
                showBottomSheet();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private FilterDecorator<Activity> getMyTravels() {
        Filter<Activity> myTravelsFilter = new Filter<Activity>() {
            @Override
            public boolean isPass(Activity item) {
                return item.getBusinessId() == agency.getId();
            }
        };

        return new FilterDecorator<>(RepositoriesFactory.getActivitiesRepository(), myTravelsFilter);
    }

    private void bindTravelDetails(Activity travel) {
        shortTravelDetails.setTravel(travel);
    }

    private void showBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
