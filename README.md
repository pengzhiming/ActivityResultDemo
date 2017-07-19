# ActivityResultDemo
如何在View中处理Activity的onActivityResult回调

public interface IActivityResult {

    Activity getActivity();

    void addActivityResultListener(ActivityResultListener activityResultListener);

    void removeActivityResultListener(ActivityResultListener activityResultListener);

    void clearActivityResultListener();
}

public interface ActivityResultListener {

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * @return interrupt ? true -> interrupt  false -> super.onActivityResult(requestCode, resultCode, data)
     */
    boolean onActivityResult(int requestCode, int resultCode, Intent data);
}

public class ActivityResultDelegate implements IActivityResult{

    private final @Nullable Activity activity;

    List<ActivityResultListener> activityResultListeners;

    public ActivityResultDelegate(Activity activity) {
        this.activity = new WeakReference(activity);
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

        if (activityResultListeners == null || activityResultListeners.isEmpty()) {
            return false;
        }

        for (ActivityResultListener activityResultListener : activityResultListeners) {
            if (activityResultListener == null) {
                continue;
            }
            if (activityResultListener.onActivityResult(requestCode, resultCode, data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addActivityResultListener(ActivityResultListener activityResultListener) {
        if (activityResultListener == null) {
            return;
        }
        if (activityResultListeners == null) {
            activityResultListeners = new ArrayList<>();
        }
        activityResultListeners.add(activityResultListener);
    }

    @Override
    public void removeActivityResultListener(ActivityResultListener activityResultListener) {
        if (activityResultListener == null) {
            return;
        }
        if (activityResultListeners != null && activityResultListeners.size() != 0) {
            activityResultListeners.remove(activityResultListener);
        }
    }

    @Override
    public void clearActivityResultListener() {
        if (activityResultListeners == null) {
            return;
        }
        activityResultListeners.clear();
    }
}

public class BaseAppCompatActivity extends AppCompatActivity implements IActivityResult{

    private final ActivityResultDelegate mDelegate;

    protected BaseAppCompatActivity() {
        mDelegate = createActivityResultDelegate();
    }

    protected ActivityResultDelegate createActivityResultDelegate() {
        return new ActivityResultDelegate(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mDelegate != null && mDelegate.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearActivityResultListener();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void addActivityResultListener(ActivityResultListener activityResultListener) {
        if (mDelegate == null) {
            return;
        }
        mDelegate.addActivityResultListener(activityResultListener);
    }

    @Override
    public void removeActivityResultListener(ActivityResultListener activityResultListener) {
        if (mDelegate == null) {
            return;
        }
        mDelegate.removeActivityResultListener(activityResultListener);
    }

    @Override
    public void clearActivityResultListener() {
        if (mDelegate == null) {
            return;
        }
        mDelegate.clearActivityResultListener();
    }
}
