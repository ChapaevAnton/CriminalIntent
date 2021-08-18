package com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime_list

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.W4ereT1ckRtB1tch.criminalintent.R
import com.W4ereT1ckRtB1tch.criminalintent.data.Crime
import com.W4ereT1ckRtB1tch.criminalintent.data.CrimeLab
import com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime.CrimePagerActivity

class CrimeListFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private var crimeAdapter: CrimeAdapter? = null
    private var updatePosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.crime_recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        updateUI()

    }

    override fun onResume() {
        super.onResume()
        updateUI()
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