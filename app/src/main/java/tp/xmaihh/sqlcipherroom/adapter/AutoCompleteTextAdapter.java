package tp.xmaihh.sqlcipherroom.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tp.xmaihh.sqlcipherroom.model.dao.UserDatabase;
import tp.xmaihh.sqlcipherroom.util.ChineseAndEnglish;
import tp.xmaihh.sqlcipherroom.util.PinYin4j;

public class AutoCompleteTextAdapter extends BaseAdapter implements Filterable {
    private List<String> list;
    private ArrayFilter filter;

    public AutoCompleteTextAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        myViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new myViewHolder();
            convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
            viewHolder.title = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (myViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    class myViewHolder {
        TextView title;
    }

    class ArrayFilter extends Filter {

        private UserDatabase recipeDatabase = UserDatabase.getINSTANCE(context);
        private PinYin4j pinyin = new PinYin4j();
        private ArrayList<String> mmmmlist;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (list == null) {
                list = new ArrayList<>();
            }

            // 如果没有过滤条件则不过滤
            if (constraint == null || constraint.length() == 0) {
                results.values = removeDuplicate(mmmmlist);
                results.count = removeDuplicate(mmmmlist).size();
            } else {
                mmmmlist = new ArrayList<>();
                mmmmlist.clear();
                Cursor cursor = recipeDatabase.query("select *from user", null);
                if (cursor.moveToFirst() == false) {
                    Log.d("T", "performFiltering: " + "没有数据");
                }
                String prex = constraint.toString().toLowerCase();
                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex("name"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));

                    //判断输入的是中文还是英文
                    if (ChineseAndEnglish.isChinese(prex)) {
                        if (title.contains(prex)) {
                            mmmmlist.add(title);
                        }
                    } else if (ChineseAndEnglish.isEnglish(prex)) {

                        Set<String> pinyinSet = pinyin.getPinyin(title);
                        Iterator<String> pinyinIT = pinyinSet.iterator();
                        while (pinyinIT.hasNext()) {
                            if (pinyinIT.next().toString().contains(prex)) {
                                mmmmlist.add(title);
                                break;
                            }
                        }
                        Set<String> pinyinAllSet = pinyin.getAllPinyin(title);
                        Iterator<String> pinyinAll = pinyinAllSet.iterator();
                        while (pinyinAll.hasNext()) {
                            if (pinyinAll.next().toString().contains(prex)) {
                                mmmmlist.add(title);
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < mmmmlist.size(); i++) {
                        Log.d("username", mmmmlist.get(i));
                    }
                }
                cursor.close();
                results.values = removeDuplicate(mmmmlist);
                results.count = removeDuplicate(mmmmlist).size();
            }
            return results;
        }

        // 在这里返回过滤结果
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // notifyDataSetInvalidated()，会重绘控件（还原到初始状态）
            // notifyDataSetChanged()，重绘当前可见区域
            list = (List<String>) results.values;
//            if (results.count > 0) {
            if (list.size() > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    /***
     *利用Set不重复的特性,去除重复对象
     * @param list
     * @return
     */

    public static List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;

    }
}