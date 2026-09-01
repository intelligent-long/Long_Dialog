package com.longx.intelligent.android.lib.longdialog.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.longx.intelligent.android.lib.longdialog.LongDialog;
import com.longx.intelligent.android.lib.longdialog.R;
import com.longx.intelligent.android.lib.longdialog.util.ResourceUtil;
import com.longx.intelligent.android.lib.longdialog.util.ThemeHelper;
import com.longx.intelligent.android.lib.longdialog.util.UiUtil;

/**
 * Created by LONG on 2026/8/28 at 下午4:54.
 */
public class ListDialog extends BaseMessageDialog<ListDialog> {
    // Custom Field for Buttons -----
    private CharSequence negativeButtonText;
    private DialogInterface.OnClickListener negativeDialogButtonYier;
    private View.OnClickListener negativeButtonYier;
    private CharSequence positiveButtonText;
    private DialogInterface.OnClickListener positiveDialogButtonYier;
    private View.OnClickListener positiveButtonYier;
    private CharSequence neutralButtonText;
    private DialogInterface.OnClickListener neutralDialogButtonYier;
    private View.OnClickListener neutralButtonYier;
    // -----

    // Custom Field for List -----
    private CharSequence[] items;
    private int itemsResId;
    private DialogInterface.OnClickListener itemClickListener;
    private int checkedItem = -1;
    private DialogInterface.OnClickListener singleChoiceClickListener;
    private boolean[] checkedItems;
    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener;
    // -----

    public ListDialog(Activity activity) {
        super(activity);
    }

    // Custom Method for Buttons -----
    public ListDialog positiveButtonText(CharSequence positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
        return this;
    }

    public ListDialog positiveButtonText(@StringRes int positiveButtonTextRes) {
        this.positiveButtonText = getContext().getString(positiveButtonTextRes);
        return this;
    }

    public ListDialog negativeButtonText(CharSequence negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
        return this;
    }

    public ListDialog negativeButtonText(@StringRes int negativeButtonTextRes) {
        this.negativeButtonText = getContext().getString(negativeButtonTextRes);
        return this;
    }

    public ListDialog neutralButtonText(CharSequence neutralButtonText) {
        this.neutralButtonText = neutralButtonText;
        return this;
    }

    public ListDialog neutralButtonText(@StringRes int neutralButtonTextRes) {
        this.neutralButtonText = getContext().getString(neutralButtonTextRes);
        return this;
    }

    public ListDialog positiveButtonYier(View.OnClickListener positiveButtonYier) {
        this.positiveButtonYier = positiveButtonYier;
        return this;
    }

    public ListDialog positiveDialogButtonYier(DialogInterface.OnClickListener positiveDialogButtonYier) {
        this.positiveDialogButtonYier = positiveDialogButtonYier;
        return this;
    }

    public ListDialog negativeButtonYier(View.OnClickListener negativeButtonYier) {
        this.negativeButtonYier = negativeButtonYier;
        return this;
    }

    public ListDialog negativeDialogButtonYier(DialogInterface.OnClickListener negativeDialogButtonYier) {
        this.negativeDialogButtonYier = negativeDialogButtonYier;
        return this;
    }

    public ListDialog neutralButtonYier(View.OnClickListener neutralButtonYier) {
        this.neutralButtonYier = neutralButtonYier;
        return this;
    }

    public ListDialog neutralDialogButtonYier(DialogInterface.OnClickListener neutralDialogButtonYier) {
        this.neutralDialogButtonYier = neutralDialogButtonYier;
        return this;
    }
    // -----

    // Custom Method for List -----
    public ListDialog items(CharSequence[] items, DialogInterface.OnClickListener listener) {
        this.items = items;
        this.itemClickListener = listener;
        return this;
    }

    public ListDialog items(@ArrayRes int itemsResId, DialogInterface.OnClickListener listener) {
        this.itemsResId = itemsResId;
        this.itemClickListener = listener;
        return this;
    }

    public ListDialog singleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
        this.items = items;
        this.checkedItem = checkedItem;
        this.singleChoiceClickListener = listener;
        return this;
    }

    public ListDialog singleChoiceItems(@ArrayRes int itemsResId, int checkedItem, DialogInterface.OnClickListener listener) {
        this.itemsResId = itemsResId;
        this.checkedItem = checkedItem;
        this.singleChoiceClickListener = listener;
        return this;
    }

    public ListDialog multiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
        this.items = items;
        this.checkedItems = checkedItems;
        this.multiChoiceClickListener = listener;
        return this;
    }

    public ListDialog multiChoiceItems(@ArrayRes int itemsResId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
        this.itemsResId = itemsResId;
        this.checkedItems = checkedItems;
        this.multiChoiceClickListener = listener;
        return this;
    }
    // -----

    private boolean hasList() {
        return (items != null || itemsResId != 0) &&
                (itemClickListener != null || singleChoiceClickListener != null || multiChoiceClickListener != null);
    }

    @Override
    protected void onBuild(MaterialAlertDialogBuilder builder) {
        boolean listPresent = hasList();
        CharSequence tempMessage = this.message;
        if (listPresent) {
            this.message = null;
        }
        super.onBuild(builder);
        this.message = tempMessage;
        if (this.positiveButtonText != null || this.positiveDialogButtonYier != null || this.positiveButtonYier != null) {
            CharSequence finalPositiveText = this.positiveButtonText != null ? this.positiveButtonText :
                    (this.okButtonText != null ? this.okButtonText :
                            getContext().getString(LongDialog.okButtonText != 0 ? LongDialog.okButtonText : android.R.string.ok));
            DialogInterface.OnClickListener listener = this.positiveDialogButtonYier != null ? this.positiveDialogButtonYier : null;
            builder.setPositiveButton(finalPositiveText, listener);
        }
        if (this.negativeButtonText != null || this.negativeDialogButtonYier != null || this.negativeButtonYier != null) {
            CharSequence finalNegativeText = this.negativeButtonText != null ? this.negativeButtonText :
                    getContext().getString(LongDialog.cancelButtonText != 0 ? LongDialog.cancelButtonText : android.R.string.cancel);
            DialogInterface.OnClickListener listener = this.negativeDialogButtonYier != null ? this.negativeDialogButtonYier : null;
            builder.setNegativeButton(finalNegativeText, listener);
        }
        if (this.neutralButtonText != null || this.neutralDialogButtonYier != null || this.neutralButtonYier != null) {
            CharSequence finalNeutralText = this.neutralButtonText != null ? this.neutralButtonText :
                    getContext().getString(LongDialog.neutralButtonText != 0 ? LongDialog.neutralButtonText : R.string.long_dialog_default_neutral_text);
            DialogInterface.OnClickListener listener = this.neutralDialogButtonYier != null ? this.neutralDialogButtonYier : null;
            builder.setNeutralButton(finalNeutralText, listener);
        }
        if (itemClickListener != null) {
            if (items != null) {
                builder.setItems(items, itemClickListener);
            } else if (itemsResId != 0) {
                builder.setItems(itemsResId, itemClickListener);
            }
        } else if (singleChoiceClickListener != null) {
            if (items != null) {
                builder.setSingleChoiceItems(items, checkedItem, singleChoiceClickListener);
            } else if (itemsResId != 0) {
                builder.setSingleChoiceItems(itemsResId, checkedItem, singleChoiceClickListener);
            }
        } else if (multiChoiceClickListener != null) {
            if (items != null) {
                builder.setMultiChoiceItems(items, checkedItems, multiChoiceClickListener);
            } else if (itemsResId != 0) {
                builder.setMultiChoiceItems(itemsResId, checkedItems, multiChoiceClickListener);
            }
        }
    }

    @Override
    protected void onCreated() {
        super.onCreated();
        setupListHeaderIfNecessary();
    }

    private void setupListHeaderIfNecessary() {
        if (hasList() && dialog != null) {
            ListView listView = dialog.getListView();
            if (listView != null) {
                Context context = getContext();
                boolean hasTitle = (title != null && title.length() > 0);
                boolean hasIcon = (iconResId != 0 || iconDrawable != null);
                boolean hasNoTitleAndIcon = !hasTitle && !hasIcon;
                boolean hasMessage = (message != null && message.length() > 0);
                if (hasMessage) {
                    TextView messageView = new TextView(context);
                    messageView.setText(message);
                    if (ThemeHelper.isM2Theme(resolvedTheme)) {
                        messageView.setTextSize(16.3f);
                        TypedValue textColorAttr = ResourceUtil.getAttr(context, android.R.attr.textColorPrimary);
                        if (textColorAttr != null) {
                            if (textColorAttr.resourceId != 0) {
                                messageView.setTextColor(ContextCompat.getColorStateList(context, textColorAttr.resourceId));
                            } else {
                                messageView.setTextColor(textColorAttr.data);
                            }
                        }
                        int paddingHorizontal = UiUtil.dpToPx(this.context, 24f);
                        int paddingTop = hasNoTitleAndIcon ? UiUtil.dpToPx(this.context, 9.6f) : 0;
                        int paddingBottom = UiUtil.dpToPx(this.context, 7f);
                        messageView.setPadding(paddingHorizontal, paddingTop, paddingHorizontal, paddingBottom);
                    } else {
                        TypedValue textAppearanceAttr = ResourceUtil.getAttr(context, com.google.android.material.R.attr.textAppearanceBodyMedium);
                        if (textAppearanceAttr == null) {
                            textAppearanceAttr = ResourceUtil.getAttr(context, android.R.attr.textAppearanceMedium);
                        }
                        if (textAppearanceAttr != null) {
                            messageView.setTextAppearance(context, textAppearanceAttr.resourceId);
                        }
                        TypedValue textColorAttr = ResourceUtil.getAttr(context, com.google.android.material.R.attr.colorOnSurfaceVariant);
                        if (textColorAttr == null) {
                            textColorAttr = ResourceUtil.getAttr(context, android.R.attr.textColorSecondary);
                        }
                        if (textColorAttr != null) {
                            if (textColorAttr.resourceId != 0) {
                                messageView.setTextColor(ContextCompat.getColorStateList(context, textColorAttr.resourceId));
                            } else {
                                messageView.setTextColor(textColorAttr.data);
                            }
                        }
                        int paddingHorizontal = UiUtil.dpToPx(this.context, 24.2f);
                        int paddingTop = hasNoTitleAndIcon ? UiUtil.dpToPx(this.context, 9.6f) : 0;
                        int paddingBottom = UiUtil.dpToPx(this.context, 7f);
                        messageView.setPadding(paddingHorizontal, paddingTop, paddingHorizontal, paddingBottom);
                    }
                    messageView.setGravity(centered ? Gravity.CENTER : Gravity.START);
                    listView.addHeaderView(messageView, null, false);
                    int headerCount = listView.getHeaderViewsCount();
                    if (singleChoiceClickListener != null && checkedItem >= 0) {
                        listView.clearChoices();
                        listView.setItemChecked(checkedItem + headerCount, true);
                    } else if (multiChoiceClickListener != null && checkedItems != null) {
                        listView.clearChoices();
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                listView.setItemChecked(i + headerCount, true);
                            }
                        }
                    }
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        int actualPosition = position - headerCount;
                        if (actualPosition < 0) return;
                        if (itemClickListener != null) {
                            itemClickListener.onClick(dialog, actualPosition);
                            dialog.dismiss();
                        } else if (singleChoiceClickListener != null) {
                            singleChoiceClickListener.onClick(dialog, actualPosition);
                        } else if (multiChoiceClickListener != null) {
                            boolean isChecked = listView.isItemChecked(position);
                            if (checkedItems != null && actualPosition < checkedItems.length) {
                                checkedItems[actualPosition] = isChecked;
                            }
                            multiChoiceClickListener.onClick(dialog, actualPosition, isChecked);
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onShowed() {
        super.onShowed();
        if (this.positiveDialogButtonYier == null && this.positiveButtonYier != null) {
            Button positiveButton = getDialog().getButton(DialogInterface.BUTTON_POSITIVE);
            if (positiveButton != null) {
                positiveButton.setOnClickListener(positiveButtonYier);
            }
        }
        if (this.negativeDialogButtonYier == null && this.negativeButtonYier != null) {
            Button negativeButton = getDialog().getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setOnClickListener(negativeButtonYier);
            }
        }
        if (this.neutralDialogButtonYier == null && this.neutralButtonYier != null) {
            Button neutralButton = getDialog().getButton(DialogInterface.BUTTON_NEUTRAL);
            if (neutralButton != null) {
                neutralButton.setOnClickListener(neutralButtonYier);
            }
        }
    }

    public int getSingleCheckedItemIndex() {
        if (dialog != null && dialog.getListView() != null) {
            ListView listView = dialog.getListView();
            if (listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
                int headerCount = listView.getHeaderViewsCount();
                int checkedPos = listView.getCheckedItemPosition();
                if (checkedPos != ListView.INVALID_POSITION) {
                    return checkedPos - headerCount;
                }
            }
        }
        return checkedItem;
    }

    public CharSequence getSingleCheckedItemValue() {
        int index = getSingleCheckedItemIndex();
        CharSequence[] currentItems = getEffectiveItems();
        if (currentItems != null && index >= 0 && index < currentItems.length) {
            return currentItems[index];
        }
        return null;
    }

    public int[] getMultiCheckedIndices() {
        if (checkedItems == null) {
            return new int[0];
        }
        int count = 0;
        for (boolean checked : checkedItems) {
            if (checked) count++;
        }
        int[] indices = new int[count];
        int index = 0;
        for (int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i]) {
                indices[index++] = i;
            }
        }
        return indices;
    }

    public CharSequence[] getMultiCheckedItemValues() {
        CharSequence[] currentItems = getEffectiveItems();
        if (currentItems == null || checkedItems == null) {
            return new CharSequence[0];
        }
        int[] checkedIndices = getMultiCheckedIndices();
        CharSequence[] result = new CharSequence[checkedIndices.length];
        for (int i = 0; i < checkedIndices.length; i++) {
            int targetIndex = checkedIndices[i];
            if (targetIndex < currentItems.length) {
                result[i] = currentItems[targetIndex];
            }
        }
        return result;
    }

    public CharSequence[] getMultiUncheckedItemValues() {
        CharSequence[] currentItems = getEffectiveItems();
        if (currentItems == null || checkedItems == null) {
            return new CharSequence[0];
        }
        int uncheckedCount = 0;
        for (boolean checked : checkedItems) {
            if (!checked) uncheckedCount++;
        }
        CharSequence[] result = new CharSequence[uncheckedCount];
        int index = 0;
        for (int i = 0; i < checkedItems.length; i++) {
            if (!checkedItems[i] && i < currentItems.length) {
                result[index++] = currentItems[i];
            }
        }
        return result;
    }

    private CharSequence[] getEffectiveItems() {
        if (items != null) {
            return items;
        } else if (itemsResId != 0 && getContext() != null) {
            return getContext().getResources().getTextArray(itemsResId);
        }
        return null;
    }
}