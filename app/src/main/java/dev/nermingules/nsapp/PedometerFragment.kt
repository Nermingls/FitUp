package dev.nermingules.nsapp

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import dev.nermingules.nsapp.databinding.FragmentPedometerBinding

class PedometerFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentPedometerBinding? = null
    private val binding get() = _binding!!

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedometerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetSteps()
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        binding.circularProgressBar.apply {
            progress = 65f
            setProgressWithAnimation(65f, 1000)
            progressMax = 200f
            progressBarColor = Color.BLACK
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.RED
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
            backgroundProgressBarColor = Color.GRAY
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.RED
            backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
            progressBarWidth = 7f
            backgroundProgressBarWidth = 3f
            roundBorder = true
            startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

        binding.circularProgressBar.onProgressChangeListener = { progress ->
            // Do something on progress change
        }

        binding.circularProgressBar.onIndeterminateModeChangeListener = { isEnable ->
            // Do something on indeterminate mode change
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
        running = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running && event != null) {
            totalSteps = event.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            binding.pedometer.text = currentSteps.toString()

            binding.circularProgressBar.setProgressWithAnimation(currentSteps.toFloat())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something on accuracy change
    }

    private fun resetSteps() {
        binding.targetPedometer.setOnClickListener {
            Toast.makeText(requireContext(), "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }
        binding.targetPedometer.setOnLongClickListener {
            previousTotalSteps = totalSteps
            binding.pedometer.text = "0"
            saveData()
            true
        }
    }

    private fun saveData() {
        val sharedPreferences = requireContext().getSharedPreferences("pedoData", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putFloat("step", previousTotalSteps)
            apply()
        }
    }

    private fun loadData() {
        val sharedPreferences = requireContext().getSharedPreferences("pedoData", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("step", 0f)
        Log.d("PedometerFragment", "$savedNumber")
        previousTotalSteps = savedNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
