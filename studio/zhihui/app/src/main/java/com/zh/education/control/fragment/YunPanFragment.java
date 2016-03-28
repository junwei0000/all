package com.zh.education.control.fragment;

import com.onedrive.sdk.concurrency.ICallback;
import com.onedrive.sdk.core.ClientException;
import com.onedrive.sdk.extensions.IOneDriveClient;
import com.onedrive.sdk.extensions.Item;
import com.zh.education.R;
import com.zh.education.control.activity.BoKeDetailActivity;
import com.zh.education.control.menu.AppApplication;
import com.zh.education.control.onedrives.DefaultCallback;
import com.zh.education.control.onedrives.DisplayItem;
import com.zh.education.control.onedrives.DisplayItemAdapter;
import com.zh.education.control.onedrives.ItemFragment;
import com.zh.education.control.onedrives.ApiExplorer;
import com.zh.education.control.activity.YunPanDetailActivity;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
		import android.view.MenuItem;
import android.view.View;
		import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class YunPanFragment extends Fragment implements AbsListView.OnItemClickListener{
	/**
	 * The item id argument string
	 */
	private static final String ARG_ITEM_ID = "itemId";

	/**
	 * The request code for simple upload
	 */
	private static final int REQUEST_CODE_SIMPLE_UPLOAD = 6767;

	/**
	 * The scheme to get content from a content resolver
	 */
	private static final String SCHEME_CONTENT = "content";

	/**
	 * The prefix for the item breadcrumb when the parent reference is unavailable
	 */
	private static final String DRIVE_PREFIX = "/drive/";

	/**
	 * Expansion options to get all children, thumbnails of children, and thumbnails
	 */
	private static final String EXPAND_OPTIONS_FOR_CHILDREN_AND_THUMBNAILS = "children(expand=thumbnails),thumbnails";

	/**
	 * Expansion options to get all children, thumbnails of children, and thumbnails when limited
	 */
	private static final String EXPAND_OPTIONS_FOR_CHILDREN_AND_THUMBNAILS_LIMITED = "children,thumbnails";

	/**
	 * The accepted file mime types for uploading to OneDrive
	 */
	private static final String ACCEPTED_UPLOAD_MIME_TYPES = "*/*";

	/**
	 * The copy destination preference key
	 */
	private static final String COPY_DESTINATION_PREF_KEY = "copy_destination";

	/**
	 * The item id for this item
	 */
	private String mItemId;

	/**
	 * The backing item representation
	 */
	private Item mItem;

	/**
	 * The listener for interacting with this fragment
	 */
//	private OnFragmentInteractionListener mListener;

	/**
	 * The Adapter which will be used to populate the ListView/GridView with
	 * Views.
	 */
	private DisplayItemAdapter mAdapter;
	private Context mContext;
	/**
	 * If the current fragment should prioritize the empty view over the visualization
	 */
	private final AtomicBoolean mEmpty = new AtomicBoolean(false);
	private IOneDriveClient oneDriveClient;
	private AppApplication app;
	private AbsListView mListView;
	private Activity activity;
	 private static final String ONEDRIVE_APP_ID = "000000004418061F";

	public YunPanFragment() {
		super();
	}

	public YunPanFragment(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		Log.e("channel_id", args.getInt("id", 0) + "云盘");


	}

	@Override

	public void onSaveInstanceState(Bundle outState) {


		super.onSaveInstanceState(outState);

		if(outState != null) {

			String FRAGMENTS_TAG = "android:support:fragments";

			// remove掉保存的Fragment

			outState.remove(FRAGMENTS_TAG);

		}

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		Log.e("云盘", "onCreateView");
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_folder_list, null);
		mListView = (AbsListView) view.findViewById(android.R.id.list);
//		app = (AppApplication)getActivity().getApplication();
		mItemId="root";
		mListView.setOnItemClickListener(this);
		((RadioButton) view.findViewById(android.R.id.button1)).setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
						if (isChecked) {
//							setFocus(ItemFocus.Visualization, getView());
						}
					}
				});

		((RadioButton)view.findViewById(android.R.id.button2)).setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
						if (isChecked) {
//							setFocus(ItemFocus.Json, getView());
						}
					}
				});


		return view;
	}



	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {


		if (isVisibleToUser&&activity!=null) {

				final ICallback<Void> serviceCreated = new DefaultCallback<Void>(activity) {
					@Override
					public void success(final Void result) {
						navigateToRoot();

					}
				};
				try {

				app = (AppApplication)activity.getApplication();
					Log.e("setUserVisibleHint", "@@@@@@@@@@@@"+activity );
					app.getOneDriveClient();
					navigateToRoot();

				} catch (final UnsupportedOperationException ignored) {
					app = (AppApplication)activity.getApplication();
					app.createOneDriveClient(activity, serviceCreated);
				}


		} else {
			// fragment不可见时不执行操作

		}

		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=getActivity();

	}
	@Override
	public void onItemClick(final AdapterView<?> parent,
							final View view, final int position,
							final long id) {

		Intent intent_boKeDetail = new Intent(activity,
                YunPanDetailActivity.class);
//		Intent intent_boKeDetail = new Intent(activity,
//				ApiExplorer.class);

		intent_boKeDetail.putExtra("mItemId", mAdapter.getItem(position).getId());
		startActivity(intent_boKeDetail);
		activity.overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);

		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(
						activity);
		SharedPreferences.Editor editor = zhedu_spf.edit();
		editor.putString("mItemId", mAdapter.getItem(position).getId());
		editor.commit();


//		mItemId=mAdapter.getItem(position).getId();
//		navigateToRoot();

//		if (null != mListener) {
//			mListener.onFragmentInteraction((DisplayItem) mAdapter.getItem(position));
//		}
	}

		/**
         * Sets the focus on one of the primary fixtures of this fragment
         *
         * @param focus The focus to appear
         * @param view the root of the fragment
         */
	private void setFocus(final ItemFocus focus, final View view) {
//		ItemFocus actualFocus = focus;
//		if (focus == ItemFocus.Visualization && mEmpty.get()) {
//			actualFocus = ItemFocus.Empty;
//		}
//
//		for (final ItemFocus focusable : ItemFocus.values()) {
//			if (focusable == actualFocus) {
//				view.findViewById(focusable.mId).setVisibility(View.VISIBLE);
//			} else {
//				view.findViewById(focusable.mId).setVisibility(View.GONE);
//			}
//		}
	}

	/**
	 * Refreshes the data for this fragment
	 */
	private void refresh() {

	}

	/**
	 * Gets the expansion options for requests on items
	 * @see {https://github.com/OneDrive/onedrive-api-docs/issues/203}
	 * @param oneDriveClient the OneDrive client
	 * @return The string for expand options
	 */
	@NonNull
	private String getExpansionOptions(final IOneDriveClient oneDriveClient) {
		final String expansionOption;
		switch (oneDriveClient.getAuthenticator().getAccountInfo().getAccountType()) {
			case MicrosoftAccount:
				expansionOption = EXPAND_OPTIONS_FOR_CHILDREN_AND_THUMBNAILS;
				break;

			default:
				expansionOption = EXPAND_OPTIONS_FOR_CHILDREN_AND_THUMBNAILS_LIMITED;
				break;
		}
		return expansionOption;
	}

	/**
	 * Creates a callback for drilling into an item
	 * @param context The application context to display messages
	 * @return The callback to refresh this item with
	 */
	private ICallback<Item> getItemCallback(final AppApplication context) {
		return new DefaultCallback<Item>(context) {
			@Override
			public void success(final Item item) {
				mItem = item;
				if (getView() != null) {
					final AbsListView mListView = (AbsListView) getView().findViewById(android.R.id.list);
					final DisplayItemAdapter adapter = (DisplayItemAdapter)mListView.getAdapter();
					if(adapter!=null){
						adapter.clear();
					}

					String text = null;
					try {
						String rawString = item.getRawObject().toString();
						final JSONObject object = new JSONObject(rawString);
						final int intentSize = 3;
						text = object.toString(intentSize);
					} catch (final Exception e) {
						Log.e(getClass().getName(), "Unable to parse the response body to json");
					}

					if (text != null) {
						((TextView) getView().findViewById(R.id.json)).setText(text);
					}

					final String fragmentLabel;
					if (mItem.parentReference != null) {
						fragmentLabel = mItem.parentReference.path
								+ context.getString(R.string.item_path_separator)
								+ mItem.name;
					} else {
						fragmentLabel = DRIVE_PREFIX + mItem.name;
					}
//					((TextView)getActivity().findViewById(R.id.fragment_label)).setText(fragmentLabel);

					mEmpty.set(item.children == null || item.children.getCurrentPage().isEmpty());

					if (item.children == null || item.children.getCurrentPage().isEmpty()) {
						final TextView emptyText = (TextView)getView().findViewById(android.R.id.empty);
						if (item.folder != null) {
							emptyText.setText(R.string.empty_list);
						} else {
							emptyText.setText(R.string.empty_file);
						}
						setFocus(ItemFocus.Empty, getView());

					} else {
						for (final Item childItem : item.children.getCurrentPage()) {
							adapter.add(new DisplayItem(adapter,
									childItem,
									childItem.id,
									context.getImageCache()));
						}
						setFocus(ItemFocus.Visualization, getView());
					}
					getActivity().invalidateOptionsMenu();
				}
			}

			@Override
			public void failure(final ClientException error) {
				if (getView() != null) {
					final TextView view = (TextView) getView().findViewById(android.R.id.empty);
					view.setText(context.getString(R.string.item_fragment_item_lookup_error, mItemId));
					setFocus(ItemFocus.Empty, getView());
				}
			}
		};
	}

	/**
	 * Navigate to the root object in the onedrive
	 */
	private void navigateToRoot() {
		mAdapter = new DisplayItemAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		final ICallback<Item> itemCallback = getItemCallback(app);
		oneDriveClient=app.getOneDriveClient();
		final String itemId;
		if (mItemId.equals("root")) {
			itemId = "root";
		} else {
			itemId = mItemId;
		}
		oneDriveClient
				.getDrive()
				.getItems(itemId)
				.buildRequest()
				.expand(getExpansionOptions(oneDriveClient))
				.get(itemCallback);

	}

	/**
	 * The available fixtures to get focus
	 */
	private enum ItemFocus {
		/**
		 * The visualization pane
		 */
		Visualization(android.R.id.list),

		/**
		 * The json response pane
		 */
		Json(R.id.json),

		/**
		 * The 'empty view' pane
		 */
		Empty(android.R.id.empty),

		/**
		 * The in progress pane
		 */
		Progress(android.R.id.progress);

		/**
		 * The resource id for the item
		 */
		private final int mId;

		/**
		 * The default constructor
		 * @param id the resource id for this item
		 */
		ItemFocus(final int id) {
			mId = id;
		}
	}




	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("云盘",   "onDestroy");
	}


}
