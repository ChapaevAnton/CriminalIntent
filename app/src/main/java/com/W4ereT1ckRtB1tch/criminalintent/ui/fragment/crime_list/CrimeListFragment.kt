package com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime_list

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.W4ereT1ckRtB1tch.criminalintent.R
import com.W4ereT1ckRtB1tch.criminalintent.data.Crime
import com.W4ereT1ckRtB1tch.criminalintent.data.CrimeLab
import com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime.CrimePagerActivity

class CrimeListFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mButtonNewCrime: Button
    private lateinit var mLinearLayout: LinearLayout
    private var crimeAdapter: CrimeAdapter? = null
    private var updatePosition = -1
    private var isVisibleSubtitle = false

    companion object {
        const val SAVED_SUBTITLE_VISIBLE = "subtitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            isVisibleSubtitle = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE, false)
        }

        mRecyclerView = view.findViewById(R.id.crime_recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mButtonNewCrime = view.findViewById(R.id.new_crime)
        mButtonNewCrime.setOnClickListener { newCrime() }
        mLinearLayout = view.findViewById(R.id.crime_list_empty)

        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem: MenuItem = menu.findItem(R.id.show_subtitle)

        if (isVisibleSubtitle) subtitleItem.setTitle(R.string.hide_subtitle) else subtitleItem.setTitle(
            R.string.show_subtitle
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                newCrime()
                true
            }
            R.id.show_subtitle -> {
                isVisibleSubtitle = !isVisibleSubtitle
                requireActivity().invalidateOptionsMenu()
                updateSubtitle()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, isVisibleSubtitle)
    }


    private fun newCrime() {
        val crime = Crime()
        CrimeLab[requireActivity()]?.addCrime(crime)
        val intent = CrimePagerActivity.newIntent(requireActivity(), crime.id)
        startActivity(intent)
    }

    private fun updateUI() {

        val crimeLab = CrimeLab[requireActivity()]
        val crimes = crimeLab?.getCrimes()

        if (crimeAdapter == null) {
            crimes?.let {
                crimeAdapter = CrimeAdapter(it)
                mRecyclerView.adapter = crimeAdapter
            }
        } else {

            //OPTIMIZE uses notifyItemChanged(int)

            //FIXME no update item resume from CrimePagerActivity
            if (updatePosition > -1) {
                //crimeAdapter?.notifyItemChanged(updatePosition)
                crimeAdapter?.notifyDataSetChanged()
                updatePosition = -1
            } else {
                crimeAdapter?.notifyDataSetChanged()
            }
        }
        updateSubtitle()
        updateEmptyList()
    }

    private fun updateSubtitle() {
        val crimeSize = CrimeLab[requireActivity()]?.getCrimes()?.size ?: -1
        val subtitle =
            if (isVisibleSubtitle) resources.getQuantityString(
                R.plurals.subtitle_plurals,
                crimeSize, crimeSize
            ) else null
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.subtitle = subtitle
    }

    private fun updateEmptyList() {
        val crimeSize = CrimeLab[requireActivity()]?.getCrimes()?.size ?: -1
        Log.d("TAG", "updateEmptyList: $crimeSize")
        if (crimeSize == 0) mLinearLayout.visibility =
            View.VISIBLE else mLinearLayout.visibility = View.INVISIBLE
    }

    private inner class CrimeHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.list_item_crime, parent, false)),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        private lateinit var mCrime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        fun bind(crime: Crime) {
            mCrime = crime
            titleTextView.text = mCrime.title
            "${
                DateFormat.getDateFormat(requireActivity()).format(mCrime.date)
            } ${
                DateFormat.getTimeFormat(requireActivity()).format(mCrime.time)
            }".also { dateTextView.text = it }
            solvedImageView.visibility = if (mCrime.solved) View.VISIBLE else View.INVISIBLE
        }

        override fun onClick(view: View?) {
            updatePosition = adapterPosition
            val intent = CrimePagerActivity.newIntent(requireActivity(), mCrime.id)
            startActivity(intent)
        }

    }

    private inner class CrimeAdapter(private val crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {

            val layoutInflater = LayoutInflater.from(requireActivity())
            return CrimeHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {

            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

    }

}