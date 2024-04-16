package com.example.clevercat.sharedClasses.extentions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zhuinden.fragmentviewbindingdelegatekt.FragmentViewBindingDelegate

/**

Use this to lazily create (and destroy) binding variable and prevent memory leaks.*
To use this you should define the variable in the fragment but also create the view normally in
onCreateView. Then you can access the value between onViewCreated and onDestroyView.

Example usage:
private val binding by viewBinding(FragmentMainBinding::bind)

override onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
return inflater.inflate(R.layout.fragment_main, container, false) => keep the xml link
}

override onViewCreated(view: View, savedInstanceState: Bundle?) {
super.onViewCreated(view, savedInstanceState)
// Use binding here!
}

Author: Gabor Varadi (Zhuinden)
Source: https://github.com/Zhuinden/fragmentviewbindingdelegate-kt
 */
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)