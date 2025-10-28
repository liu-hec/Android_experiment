package com.example.mylistviewtest;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextMenuActivity extends AppCompatActivity {

    private ListView listView;
    List<Map<String,Object>> list = null;
    private SimpleAdapter simpleAdapter;
    String[] keys = new String[]{"One","Two","Three","Four","Five"};

    //
    private ActionMode mActionMode;
    private final SparseBooleanArray selectedItems = new SparseBooleanArray(); // 记录选中项的位置


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);
        list = initData();

        listView=(ListView) findViewById(R.id.lv_list);

         simpleAdapter=new SimpleAdapter(this,list,R.layout.item_list
                ,new String[]{"description","image"},new int[]{R.id.context_text,R.id.iv_icon});

        listView.setAdapter(simpleAdapter);


        // 长按列表项，启动ActionMode
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (mActionMode == null) {
                mActionMode = startActionMode(actionModeCallback);
            }//长按唤起ActionMode
            toggleSelection(position); // 切换选中状态
            updateActionModeTitle(); // 更新“选中数量”标题
            return true;
        });

        // 点击列表项也可切换选中状态
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (mActionMode != null) {
                toggleSelection(position);
                updateActionModeTitle();
                if (selectedItems.size() == 0) {
                    mActionMode.finish(); // 无选中项时关闭ActionMode
                }
            }
        });

    }


    private List<Map<String,Object>> initData() {
        list = new ArrayList<>();
        Map<String,Object> map = null;
        for(int i=0;i<keys.length;i++){
           map = new HashMap<String,Object>();
            map.put("description",keys[i]);
            map.put("image",R.drawable.ic_android_icon);
            list.add(map);
        }
        return list;
    }

    // 切换选中状态
    private void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        // SimpleAdapter无notify方法，需重新设置Adapter更新视图
        simpleAdapter = new SimpleAdapter(this, list, R.layout.item_list,
                new String[]{"description", "image"},
                new int[]{R.id.context_text, R.id.iv_icon});
        listView.setAdapter(simpleAdapter);
    }

    // 更新ActionMode标题（显示选中数量）
    private void updateActionModeTitle() {
        if (mActionMode != null) {
            mActionMode.setTitle(selectedItems.size() + " selected");
        }
    }

    // ActionMode回调：处理菜单创建、点击、销毁
    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu); // 加载上下文菜单
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                deleteSelectedItems(); // 处理删除操作 删除ListView的items
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedItems.clear(); // 销毁时清除所有选中状态
            mActionMode = null;
        }
    };

    // 删除选中的列表项
    private void deleteSelectedItems() {
        // 从后往前删除，避免索引移位
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            int position = selectedItems.keyAt(i);
            list.remove(position);
        }
        selectedItems.clear();//清空选中的items
        // 重新设置Adapter更新视图
        simpleAdapter = new SimpleAdapter(this, list, R.layout.item_list,
                new String[]{"description", "image"},
                new int[]{R.id.context_text, R.id.iv_icon});
        listView.setAdapter(simpleAdapter);
        Toast.makeText(this, "已删除选中项", Toast.LENGTH_SHORT).show();
    }
}
