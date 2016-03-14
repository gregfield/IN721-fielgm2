package bit.fielgm2.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View fragmentView = inflater.inflate(R.layout.fragment_list_view, container, false);
            ListViewFragment fragment = new ListViewFragment();

            ListView lvCities = (ListView) fragmentView.findViewById(R.id.listView);

            Resources resourceMachine = getResources();
            String[] cityNamesArray = resourceMachine.getStringArray(R.array.cities);

            ArrayAdapter<String> cityNamesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,cityNamesArray);
            lvCities.setAdapter(cityNamesAdapter);

            return fragmentView;
        }
}
